import { useState } from "react"
import { View, Text, Pressable, ScrollView } from "react-native"
import { AcceptModal } from "../../../components/common/AcceptModal"
import { Accordion } from "../../../components/common/Accordion"
import { styles } from "./GroupInfoViewStyles"
import { MemberItem } from "./MemberItem"

export const GroupInfoView = ({ user, groupMetadata }) => {
    const [openGroupInfo, setOpenGroupInfo] = useState(false)
    const [openGroupMembers, setOpenGroupMembers] = useState(false)
    const [openDeleteYourselfModal, setOpenDeleteYourselfModal] = useState(false)
    const [openedUserDeletionModal, setOpenedUserDeletionModal] = useState(undefined)

    const organizer = getOrganizer(groupMetadata)
    const removingEnabled = user.id === organizer.userId

    const removeUserFromGroup = (userId) => {
        // Remove user from group
    }

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
                                    setOpenedUserDeletionModal(undefined)
                                    removeUserFromGroup(m.userId)
                                }}
                                acceptText={"Tak"}
                            />
                        ))}
                        {Object.values(groupMetadata.members).map((m, idx) => (
                            <MemberItem
                                key={m.userId}
                                member={m}
                                removingEnabled={removingEnabled}
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
                    setOpenDeleteYourselfModal(false)
                    removeUserFromGroup(user.id)
                }}
                acceptText={"Tak"}
            />
            <View style={{ flex: 0.15, flexDirection: 'row', alignItems: 'flex-end' }}>
                <Pressable style={styles.bottomButton} onPress={() => setOpenDeleteYourselfModal(true)}>
                    <Text style={styles.actionButtonText}>{"OPUŚĆ GRUPĘ"}</Text>
                </Pressable>
                <View style={styles.buttonPlaceholder} />
            </View>
        </View>
    )
}

const getOrganizer = (groupMetadata) => {
    const organizer = Object.values(groupMetadata.members).find(m => m.role === "ORGANIZER")
    return organizer ? organizer : { userId: -1, name: "", role: "" }
}