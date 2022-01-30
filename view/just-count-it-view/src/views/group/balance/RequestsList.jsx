import { useEffect, useState } from "react"
import { View, Text, StyleSheet, Pressable } from "react-native"
import MaterialIcons from "react-native-vector-icons/MaterialIcons";
import MaterialCommunityIcons from "react-native-vector-icons/MaterialCommunityIcons";
import { styles } from "./RequestsListStyles";
import { AcceptModal } from "../../../components/common/AcceptModal";
import { doGet } from "../../../utils/fetchUtils"

export const RequestsList = ({ user, groupMetadata }) => {
    const [financialRequests, setFinancialRequests] = useState({forDebtors:[], forMe:[]})
    const fetchRequestList = () => {
        doGet(`/api/balance/${groupMetadata.id}/currentUser`)
            .then(response => response.json())
            .then(json => setFinancialRequests(json))
            .catch(err => console.log(err.message))
    }

    useEffect(() => {
        fetchRequestList()
    }, [])
    const [openedAcceptModal, setOpenedAcceptModal] = useState(undefined)

    const acceptRequest = (requestId) => {
        // TODO: Send request acceptance
    }

    return (
        <View>

            {financialRequests.forMe.map(request => (
                <FinancialRequestForMe
                    key={request.id}
                    requestInfo={request}
                    user={user}
                    groupMetadata={groupMetadata}
                    onInfo={() => { }}
                    onReturn={() => { }}
                />
            ))}
            {financialRequests.forDebtors.map(request => {
                const price = `${request.price.toFixed(2)} ${groupMetadata.currency.symbol}`
                const debtorName = groupMetadata.members[request.debtor].name
                return (
                    <AcceptModal
                        key={request.id}
                        prompt={`Czy na pewno chcesz potwierdzić zwrot kwoty ${price} od ${debtorName}?`}
                        isVisible={openedAcceptModal === request.id}
                        onCancel={() => setOpenedAcceptModal(undefined)}
                        onAccept={() => {
                            setOpenedAcceptModal(undefined)
                            acceptRequest(request.id)
                        }}
                        acceptText={"Tak"}
                    />
                )
            })}
            {financialRequests.forDebtors.map(request => (
                <FinancialRequestForDebtor
                    key={request.id}
                    requestInfo={request}
                    user={user}
                    groupMetadata={groupMetadata}
                    onNotify={() => { }}
                    onAccept={() => setOpenedAcceptModal(request.id)}
                />
            ))}
        </View>
    )
}

const FinancialRequestForMe = ({ requestInfo, user, groupMetadata, onInfo, onReturn }) => {
    const requesterName = groupMetadata.members[requestInfo.from].name
    const verb = user.name.endsWith('a') ? 'powinna' : 'powinien'

    return (
        <View style={styles.requestContainer}>
            <Text style={styles.requestText}>{`${user.name} (Ja) ${verb} zwrócić ${requesterName}: ${requestInfo.price.toFixed(2)} ${groupMetadata.currency.symbol}`}</Text>
            <View style={styles.buttonContainer}>
                <Pressable style={styles.leftButton} onPress={onInfo}>
                    <MaterialIcons
                        name={"info"}
                        size={22}
                        style={{ paddingRight: 6 }}
                    />
                    <Text style={styles.buttonText}>Więcej opcji</Text>
                </Pressable>
                <Pressable style={styles.rightButton} onReturn={onReturn}>
                    <Text style={styles.buttonText}>Zwróć</Text>
                </Pressable>
            </View>
        </View>
    )
}

const FinancialRequestForDebtor = ({ requestInfo, user, groupMetadata, onNotify, onAccept }) => {
    const debtorName = groupMetadata.members[requestInfo.debtor].name
    const verb = debtorName.endsWith('a') ? 'powinna' : 'powinien'

    return (
        <View style={styles.requestContainer}>
            <Text style={styles.requestText}>{`${debtorName} ${verb} zwrócić ${user.name} (Ja): ${requestInfo.price.toFixed(2)} ${groupMetadata.currency.symbol}`}</Text>
            <View style={styles.buttonContainer}>
                <Pressable style={styles.leftButton} onPress={onNotify}>
                    <MaterialCommunityIcons
                        name={"bell"}

                        size={22}
                        style={{ paddingRight: 6 }}
                    />
                    <Text style={styles.buttonText}>Przypomnij</Text>
                </Pressable>
                <Pressable style={styles.rightButton} onPress={onAccept}>
                    <Text style={styles.buttonText}>Potwierdź zwrot</Text>
                </Pressable>
            </View>
        </View>
    )
}

const sampleFinancialRequests = {
    forDebtors: [
        {
            id: 1,
            debtor: 4,
            price: 4.00,
        },
        {
            id: 2,
            debtor: 2,
            price: 26.50,
        }
    ],
    forMe: [
        {
            id: 3,
            from: 3,
            price: 15.00,
        },
        {
            id: 4,
            from: 2,
            price: 56.21,
        },
    ]
}
