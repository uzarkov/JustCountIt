import { StyleSheet } from "react-native";

export const globalStyles = StyleSheet.create({
    inputAndroid: {
        marginBottom: 20,
        borderWidth: 2,
        borderRadius: 14,
        borderColor: '#03DAC5',
        fontSize: 20,
        color: 'white',
        textAlign: 'auto',
        paddingHorizontal: 15,
    },
    button: {
        marginVertical: 10,
        borderRadius: 20,
        backgroundColor: '#03DAC5',
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
        height: 450,
        backgroundColor: "#252525",
        borderRadius: 20
    },
    modalTitleContainer: {
        marginTop: '1%',
        marginBottom: '10%',
        flexDirection: 'row',
        justifyContent: 'space-between',
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