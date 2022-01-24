import { View, Text, ScrollView, StyleSheet } from "react-native"
import { BalanceChart } from "./BalanceChart"
import { RequestsList } from "./RequestsList"

export const BalanceView = ({ user, groupMetadata }) => {
    return (
        <View style={styles.container}>
            <ScrollView>
                <Text style={styles.headerText}>Bilans</Text>
                <View>
                    <BalanceChart user={user} groupMetadata={groupMetadata} />
                </View>
                <Text style={styles.headerText}>Moje zwroty kosztów</Text>
                <View>
                    <RequestsList user={user} groupMetadata={groupMetadata} />
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