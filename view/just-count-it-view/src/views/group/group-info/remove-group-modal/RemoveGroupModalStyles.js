import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    modal: {
      borderColor: '#CF6679',
        borderWidth: 1
    },
    message: {
      color:'white',
      fontSize: 22,
      paddingHorizontal: 25,
      paddingBottom: 50
    },
    buttonsContainer: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        paddingBottom: 10,
        paddingHorizontal: 30
    },
    buttonText: {
        letterSpacing: 1,
        color: '#CF6679',
        fontSize: 20
    },
})