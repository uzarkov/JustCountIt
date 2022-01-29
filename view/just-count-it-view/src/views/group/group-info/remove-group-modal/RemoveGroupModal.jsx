import React from "react";
import {Pressable, Text, View} from "react-native";
import Modal from "react-native-modal";
import {globalStyles} from "../../../../styles/globalStyles";
import {styles} from "./RemoveGroupModalStyles"

export const RemoveGroupModal = ({isVisible, onCancel, onAccept, withWarning}) => {

    const baseMessage = "Czy na pewno chcesz usunąć grupę Dom Tiktokerów?";
    const finalMessage = withWarning ? baseMessage + " Nie wszystkie wydatki zostały rozliczone." : baseMessage;

    return (
        <Modal
            isVisible={isVisible}
            avoidKeyboard={true}
            backdropOpacity={0.9}
            onBackdropPress={onCancel}
            backdropTransitionOutTiming={0}
        >
            <View style={globalStyles.modalContainer}>
                <View style={[globalStyles.modal, styles.modal]}>
                    <View style={globalStyles.modalTitleContainer}>
                        <Text style={globalStyles.modalTitle}>
                            Usuwanie grupy
                        </Text>
                    </View>
                    <Text style={styles.message}>{finalMessage}</Text>
                    <View style={styles.buttonsContainer}>
                        <Pressable
                            style={globalStyles.iconButton}
                            onPress={onCancel}
                        >
                            <Text style={styles.buttonText}>
                                ANULUJ
                            </Text>
                        </Pressable>
                        <Pressable
                            style={globalStyles.iconButton}
                            onPress={onAccept}
                        >
                            <Text style={styles.buttonText}>
                                USUŃ
                            </Text>
                        </Pressable>
                    </View>
                </View>
            </View>
        </Modal>
    )
}