import { useState } from "react";
import {FlatList, Pressable, Text, View} from "react-native"
import { TopBar } from "../../components/top-bar/TopBar";
import { globalStyles } from "../../styles/globalStyles";
import { GroupViewContainer } from "../group/GroupViewContainer";
import MaterialIcons from 'react-native-vector-icons/MaterialIcons';
import { styles } from './GroupsViewStyles';
import ActionButton from "react-native-action-button";
import {GroupItem} from "./group-item/GroupItem";
import {CreateGroupModal} from "./create-group-modal/CreateGroupModal";


export const GroupsView = ({ user, logout }) => {
    const [chosenGroup, setChosenGroup] = useState(undefined)
    const [createGroupModalOpened, isCreateGroupModalOpened] = useState(false);
    const [groups, setGroups] = useState(sampleGroupMetadata);

    if (chosenGroup) {
        return (
            <GroupViewContainer
                user={user}
                groupMetadata={chosenGroup}
                onExit={() => setChosenGroup(undefined)}
            />
        )
    }

    //TODO
    const fetchGroups = () => {

    }

    return (
        <>
        <TopBar text={"JustCountIt"} onExit={logout} />
        <View style={styles.container}>
            {/* TODO: Fetch and list available groups metadata here */}
            <View style={styles.titleContainer}>
                <Text style={styles.title}>
                    Twoje grupy
                </Text>
            </View>
            <View style={styles.listContainer}>
                <FlatList
                    data={groups}
                    renderItem={({item}) => (
                        <GroupItem
                            key={item.id}
                            name={item.name}
                            description={item.description}
                            onClick={() => setChosenGroup(item)}
                        />
                    )}
                />
            </View>
        </View>
         <CreateGroupModal
            modalVisible={createGroupModalOpened}
            setModalVisible={isCreateGroupModalOpened}
         />
        <ActionButton
            buttonColor="#BB86FC"
            buttonTextStyle={{ color: '#000' }}
            bgColor="rgba(0,0,0,0.9)"
        >
            <ActionButton.Item
                buttonColor='#03DAC5'
                title="Dołącz do grupy"
                onPress={() => console.log("Join group modal")}
            >
                <MaterialIcons
                    name={"group-add"}
                    color={"#000"}
                    size={28}
                />
            </ActionButton.Item>
            <ActionButton.Item
                buttonColor='#03DAC5'
                title="Utwórz grupę"
                onPress={() => isCreateGroupModalOpened(true)}
            >
                <MaterialIcons
                    name={"create"}
                    color={"#000"}
                    size={28}
                />
            </ActionButton.Item>
        </ActionButton>
        </>
    )
}

const sampleGroupMetadata = [
    {
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
    },
    {
        id: 2,
        name: "Moja grupa",
        description: "Opis mojej grupy",
        currency: {
            name: "PLN",
            symbol: "zł"
        },
        members: {

        }
    }
]