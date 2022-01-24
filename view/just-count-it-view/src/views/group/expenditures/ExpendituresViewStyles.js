import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container: {
        height: '100%',
        backgroundColor: '#353535',
    },
    footerContainer: {
        backgroundColor: '#222222',
        flexDirection: 'column',
        height: '100%',
        paddingTop: 2,
    },
    textContainer: {
        flexDirection: 'row',
        paddingHorizontal: 12,
        marginBottom: 2,
    },
    topText: {
        flex: 1,
        color: 'whitesmoke',
        fontSize: 16,
        paddingTop: 6,
    },
    bottomText: {
        flex: 1,
        color: '#c0c0c0',
        fontSize: 14,
    },
})