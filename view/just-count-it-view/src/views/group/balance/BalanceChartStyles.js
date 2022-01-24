import { StyleSheet } from "react-native"

export const styles = StyleSheet.create({
    container: {
        paddingHorizontal: 12,
        margin: 12,
    },
    rowContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        marginTop: 4,
    },
    leftProgressBarText: {
        position: 'absolute',
        flex: 0,
        transform: [{ rotateY: '180deg' }],
        fontSize: 16,
        padding: 1,
        marginLeft: 4,
        color: 'whitesmoke'
    },
    leftProgressBar: {
        flex: 1,
        marginRight: 16,
        transform: [{ rotateY: '180deg' }]
    },
    rightProgressBarText: {
        position: 'absolute',
        flex: 0,
        fontSize: 16,
        padding: 1,
        marginLeft: 4,
        color: 'whitesmoke'
    },
    rightProgressBar: {
        flex: 1,
        marginLeft: 16,
    },
    text: {
        flex: 1,
        color: 'whitesmoke',
        fontSize: 18
    },
    stripe: {
        alignSelf: 'center',
        width: '100%',
        height: 2,
        backgroundColor: '#454545',
        marginTop: 4,
    },
})