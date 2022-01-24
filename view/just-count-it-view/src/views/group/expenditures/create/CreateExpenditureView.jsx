import React, { useState } from "react"
import { View, Text, Pressable, ScrollView } from "react-native"
import { InputField } from "../../../../components/common/InputField"
import { styles } from "./CreateExpenditureViewStyles"
import MaterialCommunityIcons from "react-native-vector-icons/MaterialCommunityIcons";
import { GroupMemberCheckboxItem } from "./GroupMemberCheckboxItem";
import { AcceptModal } from "../../../../components/common/AcceptModal";

export const CreateExpenditureView = ({ user, groupMetadata, onCancel }) => {
    const [groupMembers, setGroupMembers] = useState(sampleGroupMembers)
    const [selectedMembers, setSelectedMembers] = useState([])
    const [showAcceptModal, setShowAcceptModal] = useState(false)

    const titleRef = React.createRef()
    const priceRef = React.createRef()

    const createExpenditure = () => {
        const title = titleRef.current.value()
        const price = priceRef.current.value()
        const debtors = [...selectedMembers]

        // TODO: Post expenditure creation request

        titleRef.current.clear()
        priceRef.current.clear()
        setSelectedMembers([])
        onCancel()
    }

    return (
        <View style={styles.container}>
            <AcceptModal
                prompt={"Czy na pewno chcesz dodać wydatek?"}
                isVisible={showAcceptModal}
                onCancel={() => setShowAcceptModal(false)}
                onAccept={() => {
                    setShowAcceptModal(false)
                    createExpenditure()
                }}
                acceptText={"Dodaj"}
            />
            <View style={styles.navContainer}>
                <NavButton text={"Anuluj"} isActive={false} onClick={onCancel} />
                <NavButton text={"Nowy wydatek"} isActive={true} />
                <NavButton text={""} isActive={false} />
            </View>
            <InputField
                label={"Tytuł"}
                otherProps={{
                    containerStyle: { paddingTop: 12, paddingHorizontal: 8 },
                    inputContainerStyle: { backgroundColor: '#404040' }
                }}
                inputRef={titleRef}
            />
            <View style={styles.rowContainer}>
                <InputField
                    label={"Cena"}
                    otherProps={{
                        suffix: groupMetadata.currency.name,
                        keyboardType: "decimal-pad",
                        containerStyle: { flex: 1, paddingHorizontal: 8 },
                        inputContainerStyle: { backgroundColor: '#404040' }
                    }}
                    inputRef={priceRef}
                />
                <Pressable style={styles.actionButton}>
                    <Text style={styles.actionButtonText}>{"SKANUJ RACHUNEK"}</Text>
                </Pressable>
            </View>
            <Text style={styles.headerText}>Kto jest należny?</Text>
            <View style={{ flex: 0.85 }}>
                <ScrollView>
                    {groupMembers.map(member => (
                        <GroupMemberCheckboxItem
                            key={member.userId}
                            name={member.username}
                            onValueChange={(isChecked) => {
                                if (isChecked) {
                                    setSelectedMembers([...selectedMembers, member])
                                } else {
                                    setSelectedMembers(selectedMembers.filter(m => m.userId !== member.userId))
                                }
                            }}
                        />
                    ))}
                </ScrollView>
            </View>
            <View style={{ flex: 0.1, flexDirection: 'row' }}>
                <View style={styles.buttonPlaceholder} />
                <Pressable style={styles.bottomButton} onPress={() => setShowAcceptModal(true)}>
                    <MaterialCommunityIcons
                        name={"plus-thick"}
                        size={22}
                        style={{ paddingRight: 6 }}
                    />
                    <Text style={styles.actionButtonText}>{"DODAJ WYDATEK"}</Text>
                </Pressable>
            </View>
        </View>
    )
}

const NavButton = ({ text, isActive, onClick = () => { } }) => {
    const textColor = isActive ? '#BB86FC' : 'whitesmoke'
    const stripeColor = isActive ? '#BB86FC' : '#222222'

    return (
        <Pressable style={styles.button} onPress={() => onClick()}>
            <Text style={[styles.buttonText, { color: textColor }]}>{text}</Text>
            <View style={[styles.activeStripe, { backgroundColor: stripeColor }]}></View>
        </Pressable>
    )
}

const sampleGroupMembers = [
    {
        userId: 101,
        username: 'Andrzej'
    },
    {
        userId: 102,
        username: 'Julia'
    },
    {
        userId: 103,
        username: 'Robert'
    },
]