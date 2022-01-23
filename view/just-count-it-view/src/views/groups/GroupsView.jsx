import { useState } from "react";
import { Pressable, Text, View } from "react-native"
import { TopBar } from "../../components/top-bar/TopBar";
import { globalStyles } from "../../styles/globalStyles";
import { GroupViewContainer } from "../group/GroupViewContainer";
import { styles } from './GroupsViewStyles';


export const GroupsView = ({ user, logout }) => {
    const [chosenGroup, setChosenGroup] = useState(undefined)

    if (chosenGroup) {
        return (
            <GroupViewContainer
                groupMetadata={chosenGroup}
                onExit={() => setChosenGroup(undefined)}
            />
        )
    }

    return (
        <View style={styles.container}>
            <TopBar text={"JustCountIt"} onExit={logout} />
            {/* TODO: Fetch and list available groups metadata here */}
            <Pressable
                style={[globalStyles.button, styles.button]}
                onPress={() => setChosenGroup(sampleGroupMetadata)}
            >
                <Text style={styles.buttonText}>{"Przejdź do grupy testowej"}</Text>
            </Pressable>
        </View>
    )
}

const sampleGroupMetadata = {
    id: 1,
    name: "Grupa testowa",
    description: "Opis grupy testowej",
}