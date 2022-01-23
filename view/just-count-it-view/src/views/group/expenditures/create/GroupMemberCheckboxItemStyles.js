import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    textContainer: {
        flexDirection: 'row',
        paddingHorizontal: 12,
        marginBottom: 2,
    },
    memberText: {
        flex: 1,
        color: 'whitesmoke',
        fontSize: 18,
    },
    stripe: {
        alignSelf: 'center',
        width: '98%',
        height: 2,
        backgroundColor: '#454545',
        marginTop: 8,
    },
    groupMemberContainer: {
        flexDirection: 'column',
        backgroundColor: '#353535',
        paddingTop: 4,
        paddingHorizontal: 12,
        marginTop: 8,
    }
})