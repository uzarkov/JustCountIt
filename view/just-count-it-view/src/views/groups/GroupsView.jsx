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
                user={user}
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
    currency: {
        name: "PLN",
        symbol: "zł"
    },
    members: {
        101: {
            userId: 101,
            name: "Andrzej",
            role: "ORGANIZER"
        },
        102: {
            userId: 102,
            name: "Julia",
            role: "PAYMASTER"
        },
        103: {
            userId: 103,
            name: "Robert",
            role: "MEMBER"
        },
        104: {
            userId: 104,
            name: "Jan",
            role: "MEMBER",
        },
        105: {
            userId: 105,
            name: "Tomasz",
            role: "MEMBER",
        },
    }
}