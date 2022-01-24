import Modal from "react-native-modal";
import { View, Text, Pressable, StyleSheet } from "react-native"
import { globalStyles } from "../../styles/globalStyles";

export const AcceptModal = ({ prompt, isVisible, onCancel, onAccept, acceptText, cancelText = "Anuluj" }) => {
    return (
        <Modal
            isVisible={isVisible}
            avoidKeyboard={true}
            backdropOpacity={0.9}
            onBackdropPress={() => onCancel()}
            backdropTransitionOutTiming={0}
        >
            <View style={globalStyles.modalContainer}>
                <View style={globalStyles.modal}>
                    <Text style={styles.prompt}>{prompt}</Text>
                    <View style={{ flexDirection: 'row', justifyContent: "space-between" }}>
                        <Pressable
                            style={styles.button}
                            onPress={() => onCancel()}
                        >
                            <Text style={styles.buttonText}>{cancelText}</Text>
                        </Pressable>
                        <Pressable
                            style={styles.button}
                            onPress={() => onAccept()}
                        >
                            <Text style={styles.buttonText}>{acceptText}</Text>
                        </Pressable>
                    </View>
                </View>
            </View>
        </Modal>
    )
}

const styles = StyleSheet.create({
    prompt: {
        color: 'white',
        fontSize: 24,
        fontWeight: 'bold',
        padding: 12,
        textAlign: 'center'
    },
    button: {
        alignItems: 'center',
        backgroundColor: '#BB86FC',
        padding: 5,
        marginHorizontal: 12,
        borderRadius: 8,
        paddingHorizontal: 20,
    },
    buttonText: {
        fontSize: 16,
        fontWeight: 'bold',
        textAlign: 'center',
    },
})