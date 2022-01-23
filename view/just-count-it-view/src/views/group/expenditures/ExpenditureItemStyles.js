import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container: {
        flexDirection: 'column',
        backgroundColor: '#353535',
        paddingTop: 4,
        marginTop: 4,
    },
    textContainer: {
        flexDirection: 'row',
        paddingHorizontal: 12,
        marginBottom: 2,
    },
    stripe: {
        alignSelf: 'center',
        width: '98%',
        height: 2,
        backgroundColor: '#454545',
        marginTop: 8,
    },
    topText: {
        flex: 1,
        color: 'whitesmoke',
        fontSize: 18,
    },
    bottomText: {
        flex: 1,
        color: '#c0c0c0',
        fontSize: 14,
    },
})