import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#353535',
    },
    accordionContainer: {
        paddingTop: 4,
        marginTop: 4,
        marginBottom: 2,
        marginHorizontal: 20,
    },
    textContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        paddingHorizontal: 4,
        marginTop: 4,
    },
    leftText: {
        fontSize: 18,
        color: "#BB86FC"
    },
    rightText: {
        fontSize: 18,
        color: 'whitesmoke'
    },
    buttonPlaceholder: {
        flex: 1,
        padding: 6,
        marginHorizontal: 12,
        marginBottom: 8,
        borderRadius: 4,
    },
    bottomButton: {
        flex: 1,
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#e04744',
        padding: 5,
        marginLeft: 12,
        marginRight: 135,
        borderRadius: 4,
    },
    actionButtonText: {
        fontSize: 16,
        fontWeight: 'bold',
        textAlign: 'center',
    },
})