import { Pressable, Text, View } from "react-native"
import { globalStyles } from "../../styles/globalStyles";
import { styles } from './GroupsViewStyles';


export const GroupsView = ({ user, logout }) => {
    return (
        <View style={styles.container}>
            <Pressable
                style={[globalStyles.button, styles.button]}
                onPress={() => logout()}
            >
                <Text style={styles.buttonText}>{"Wyloguj"}</Text>
            </Pressable>
        </View>
    )
}