import { useEffect, useState } from "react"
import { View, Text, Pressable, ScrollView } from "react-native"
import { AcceptModal } from "../../../components/common/AcceptModal"
import { Accordion } from "../../../components/common/Accordion"
import { styles } from "./GroupInfoViewStyles"
import { MemberItem } from "./MemberItem"
import MaterialIcons from "react-native-vector-icons/MaterialIcons";
import { RemoveGroupModal } from "./remove-group-modal/RemoveGroupModal";
import { doDelete, doGet } from "../../../utils/fetchUtils"
import { showErrorToast, showSuccessToast } from "../../../utils/toasts";

export const GroupInfoView = ({ user, groupMetadata, updateGroupMetadata, onExit, removeGroup }) => {
    const [openGroupInfo, setOpenGroupInfo] = useState(false)
    const [openGroupMembers, setOpenGroupMembers] = useState(false)
    const [openDeleteYourselfModal, setOpenDeleteYourselfModal] = useState(false)
    const [openedUserDeletionModal, setOpenedUserDeletionModal] = useState(undefined)
    const [removeGroupModalOpened, setRemoveGroupModalOpened] = useState(false)
    const [balance, setBalance] = useState([])

    const organizer = getOrganizer(groupMetadata)
    const removingEnabled = user.id === organizer.userId

    const removeUserFromGroup = (userId) => {
        doDelete(`/api/groups/${groupMetadata.id}/user/${userId}`)
            .then(response => {
                const newMembers = { ...groupMetadata.members }
                delete newMembers[userId]
                updateGroupMetadata({
                    ...groupMetadata,
                    members: newMembers
                })
            })
            .catch(err => console.log(err.message))
    }

    const deleteGroup = () => {
        doDelete(`/api/groups/${groupMetadata.id}`)
            .then(response => {
                if (response.ok)
                    showSuccessToast(`Grupa ${groupMetadata.name} została pomyślnie usunięta`)
                else
                    showErrorToast("Nie udało się usunąć grupy")
            })
            .catch(err => console.log(err.message))
        removeGroup(groupMetadata.id)
        onExit()
    }

    const fetchGroupBalance = () => {
        doGet(`/api/balance/${groupMetadata.id}`)
            .then(response => response.json())
            .then(json => setBalance(json))
            .catch(err => console.log(err.message))
    }

    useEffect(() => {
        fetchGroupBalance()
    }, [])

    return (
        <View style={styles.container}>
            <View style={styles.accordionContainer}>
                <Accordion
                    isOpen={openGroupInfo}
                    onClose={() => setOpenGroupInfo(false)}
                    onOpen={() => setOpenGroupInfo(true)}
                    text={"Informacje o grupie"}
                >
                    <View style={styles.textContainer}>
                        <Text style={styles.leftText}>{"Nazwa"}</Text>
                        <Text style={styles.rightText}>{groupMetadata.name}</Text>
                    </View>
                    <View style={styles.textContainer}>
                        <Text style={styles.leftText}>{"Opis"}</Text>
                        <Text style={styles.rightText}>{groupMetadata.description}</Text>
                    </View>
                    <View style={styles.textContainer}>
                        <Text style={styles.leftText}>{"Waluta"}</Text>
                        <Text style={styles.rightText}>{groupMetadata.currency.name}</Text>
                    </View>
                    <View style={styles.textContainer}>
                        <Text style={styles.leftText}>{"Organizator"}</Text>
                        <Text style={styles.rightText}>{organizer.name}</Text>
                    </View>
                </Accordion>
            </View>
            <View style={[styles.accordionContainer, { flex: 0.82 }]}>
                <Accordion
                    isOpen={openGroupMembers}
                    onClose={() => setOpenGroupMembers(false)}
                    onOpen={() => setOpenGroupMembers(true)}
                    text={"Członkowie grupy"}
                >
                    <ScrollView>
                        {Object.values(groupMetadata.members).map(m => (
                            <AcceptModal
                                key={m.userId}
                                // TODO: Add info about unsettled expenses if they exist to prompt (include them in group metadata if possible) 
                                prompt={`Czy na pewno chcesz usunąć użytkownika ${m.name} z grupy?`}
                                isVisible={openedUserDeletionModal === m.userId}
                                onCancel={() => setOpenedUserDeletionModal(undefined)}
                                onAccept={() => {
                                    removeUserFromGroup(m.userId)
                                }}
                                acceptText={"Tak"}
                            />
                        ))}
                        {Object.values(groupMetadata.members).map((m, idx) => (
                            <MemberItem
                                key={m.userId}
                                member={m}
                                removingEnabled={removingEnabled && m.userId !== user.id}
                                onRemove={() => setOpenedUserDeletionModal(m.userId)}
                            />
                        ))}
                    </ScrollView>
                </Accordion>
            </View>
            <AcceptModal
                prompt={`Czy na pewno chcesz opuścić grupę?`}
                isVisible={openDeleteYourselfModal}
                onCancel={() => setOpenDeleteYourselfModal(false)}
                onAccept={() => {
                    removeUserFromGroup(user.id)
                }}
                acceptText={"Tak"}
            />
            <RemoveGroupModal
                isVisible={removeGroupModalOpened}
                onCancel={() => setRemoveGroupModalOpened(false)}
                onAccept={() => deleteGroup()}
                withWarning={balance.filter(user => user.balance !== 0).length !== 0}
            />
            <View style={{ flex: 0.15, flexDirection: 'row', alignItems: 'flex-end' }}>
                <Pressable style={styles.bottomButton} onPress={() => setOpenDeleteYourselfModal(true)}>
                    <Text style={styles.actionButtonText}>{"OPUŚĆ GRUPĘ"}</Text>
                </Pressable>
                <View style={styles.buttonPlaceholder} />
                {removingEnabled ? <Pressable
                    style={styles.removeGroupButton}
                    onPress={() => setRemoveGroupModalOpened(true)}
                >
                    <MaterialIcons
                        name={"delete"}
                        color={"black"}
                        size={35}
                    />
                </Pressable> : <></>}
            </View>
        </View>
    )
}

const getOrganizer = (groupMetadata) => {
    const organizer = Object.values(groupMetadata.members).find(m => m.role === "ORGANIZER")
    return organizer ? organizer : { userId: -1, name: "", role: "" }
}