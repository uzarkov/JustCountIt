import { useEffect, useState } from "react"
import { View, Text, ScrollView, StyleSheet } from "react-native"
import { doGet } from "../../../utils/fetchUtils"
import { BalanceChart } from "./BalanceChart"
import { RequestsList } from "./RequestsList"

export const BalanceView = ({ user, groupMetadata }) => {
    const [groupBalanceMetadata, setGroupBalanceMetadata] = useState([])

    const fetchGroupBalance = () => {
        doGet(`/api/balance/${groupMetadata.id}`)
            .then(response => response.json())
            .then(json => setGroupBalanceMetadata(json))
            .catch(err => console.log(err.message))
    }

    useEffect(() => {
        fetchGroupBalance()
    }, [])

    return (
        <View style={styles.container}>
            <ScrollView>
                <Text style={styles.headerText}>Bilans</Text>
                <View>
                    <BalanceChart user={user} groupMetadata={groupMetadata} groupBalanceMetadata={groupBalanceMetadata} />
                </View>
                <Text style={styles.headerText}>Moje zwroty kosztów</Text>
                <View>
                    <RequestsList user={user} groupMetadata={groupMetadata} fetchGroupBalance={fetchGroupBalance} />
                </View>
            </ScrollView>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#353535',
    },
    headerText: {
        color: '#BB86FC',
        fontSize: 24,
        margin: 12,
        paddingHorizontal: 12,
        textAlign: 'center'
    },
})