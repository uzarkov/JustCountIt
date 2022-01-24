import BouncyCheckbox from "react-native-bouncy-checkbox";
import { View, Text } from "react-native"
import { styles } from "./GroupMemberCheckboxItemStyles";

export const GroupMemberCheckboxItem = ({ name, onValueChange }) => {
    return (
        <View style={styles.groupMemberContainer}>
            <View style={styles.textContainer}>
                <Text style={[styles.memberText, { textAlign: 'left' }]}>{name}</Text>
                <BouncyCheckbox
                    size={20}
                    isChecked={false}
                    fillColor={'#BB86FC'}
                    iconStyle={{ borderRadius: 4 }}
                    onPress={(isChecked) => onValueChange(isChecked)}
                />
            </View>
            <View style={styles.stripe}></View>
        </View>
    )
}