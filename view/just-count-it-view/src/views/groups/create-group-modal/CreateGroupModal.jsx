import React from "react";
import { Text, View} from "react-native";
import Modal from "react-native-modal";
import {globalStyles} from "../../../styles/globalStyles";
import {styles} from "./CreateGroupModalStyles";
import {CreateGroupForm} from "../create-group-form/CreateGroupForm";

export const CreateGroupModal = ({modalVisible,  setModalVisible}) => {

    return (
        <Modal
            isVisible={modalVisible}
            avoidKeyboard={true}
            backdropOpacity={0.9}
            onBackdropPress={() => setModalVisible(false)}
            backdropTransitionOutTiming={0}
        >
            <View style={globalStyles.modalContainer}>
                <View style={[globalStyles.modal, styles.modal]}>
                    <View style={globalStyles.modalTitleContainer}>
                        <Text style={globalStyles.modalTitle}>
                            Tworzenie grupy
                        </Text>
                    </View>
                    <CreateGroupForm showModal={setModalVisible}/>
                </View>
            </View>

        </Modal>
    )
}