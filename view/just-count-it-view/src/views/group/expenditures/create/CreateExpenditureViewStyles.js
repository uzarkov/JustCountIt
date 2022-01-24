import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#353535',
    },
    navContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#222222',
    },
    rowContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        paddingTop: 8,
    },
    button: {
        flex: 1,
        height: 40,
        paddingTop: 6,
    },
    buttonText: {
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'center',
    },
    buttonPlaceholder: {
        flex: 1,
        padding: 6,
        marginHorizontal: 12,
        marginBottom: 8,
        borderRadius: 4,
    },
    actionButton: {
        flex: 1,
        backgroundColor: '#BB86FC',
        padding: 6,
        marginHorizontal: 12,
        marginBottom: 8,
        borderRadius: 4,
    },
    bottomButton: {
        flex: 1,
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#BB86FC',
        padding: 5,
        marginHorizontal: 12,
        borderRadius: 4,
    },
    actionButtonText: {
        fontSize: 16,
        fontWeight: 'bold',
        textAlign: 'center',
    },
    activeStripe: {
        width: '100%',
        height: 2,
        marginTop: 8
    },
    headerText: {
        color: '#BB86FC',
        fontSize: 16,
        margin: 12
    },
})