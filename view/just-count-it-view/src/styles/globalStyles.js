import { StyleSheet } from "react-native";

export const globalStyles = StyleSheet.create({
    inputAndroid: {
        marginBottom: 20,
        borderWidth: 1,
        borderRadius: 14,
        borderColor: '#BB86FC',
        fontSize: 20,
        color: 'white',
        textAlign: 'auto',
        paddingHorizontal: 15,
    },
    button: {
        marginVertical: 10,
        borderRadius: 20,
        backgroundColor: '#BB86FC',
        alignItems: 'center',
        justifyContent: 'center'
    },
    iconButton: {
        backgroundColor: 'transparent',
        borderWidth: 0
    },
    shadowBox: {
        backgroundColor: '#383838',
        shadowColor: "#000",
        shadowOffset: {
            width: 0,
            height: 5,
        },
        shadowOpacity: 0.34,
        shadowRadius: 6.27,
        elevation: 10,
        marginTop: '3%',
        width: '95%',
        padding: 10,
        borderRadius: 10,
    },
    modalContainer: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    modal: {
        width: '85%',
        backgroundColor: "#252525",
        borderRadius: 20,
        paddingBottom: 12,
    },
    modalTitleContainer: {
        marginTop: '1%',
        marginBottom: '10%',
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        paddingHorizontal: 5,
    },
    modalTitle: {
        color: 'white',
        fontSize: 30,
        fontWeight: 'bold'
    },
    longList: {
        marginLeft: 8,
        height: '80%'
    },
    accordionList: {
        marginLeft: 8,
        minHeight: 30,
        maxHeight: 170
    }
})