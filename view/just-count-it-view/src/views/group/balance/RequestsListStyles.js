import { StyleSheet } from "react-native"

export const styles = StyleSheet.create({
    requestContainer: {
        flexDirection: 'column',
        paddingHorizontal: 12,
        paddingBottom: 10,
        margin: 8,
        borderWidth: 1,
        borderColor: '#454545',
        borderRadius: 4,
    },
    requestText: {
        color: 'whitesmoke',
        fontSize: 16,
        padding: 8,
    },
    headerText: {
        color: '#BB86FC',
        fontSize: 20,
        margin: 12,
        paddingHorizontal: 12,
        textAlign: 'center'
    },
    buttonContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between'
    },
    leftButton: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: '#BB86FC',
        padding: 4,
        borderRadius: 4,
        marginRight: 30,
        marginLeft: 10,
    },
    rightButton: {
        flex: 1,
        backgroundColor: '#BB86FC',
        padding: 4,
        borderRadius: 4,
        marginLeft: 30,
        marginRight: 10,
    },
    buttonText: {
        textAlign: 'center',
        fontSize: 16,
        padding: 0,
    }
})