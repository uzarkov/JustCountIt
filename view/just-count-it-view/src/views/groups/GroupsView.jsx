import { useEffect, useState } from "react";
import { FlatList, Text, View } from "react-native"
import { TopBar } from "../../components/top-bar/TopBar";
import { GroupViewContainer } from "../group/GroupViewContainer";
import MaterialIcons from 'react-native-vector-icons/MaterialIcons';
import { styles } from './GroupsViewStyles';
import ActionButton from "react-native-action-button";
import { GroupItem } from "./group-item/GroupItem";
import { CreateGroupModal } from "./create-group-modal/CreateGroupModal";
import { doGet } from "../../utils/fetchUtils";

export const GroupsView = ({ user, logout }) => {
    const [chosenGroup, setChosenGroup] = useState(undefined)
    const [createGroupModalOpened, isCreateGroupModalOpened] = useState(false);
    const [groups, setGroups] = useState([]);

    const fetchGroupsMetadata = () => {
        doGet('/api/groups')
            .then(response => response.json())
            .then(json => setGroups(json))
            .catch(err => console.log(err))
    }

    const fetchSpecificGroup = (groupId) => {
        doGet(`/api/groups/${groupId}/metadata`)
            .then(response => response.json())
            .then(json => setChosenGroup(json))
            .catch(err => console.log(err))
    }

    useEffect(() => {
        fetchGroupsMetadata()
    }, [])

    const addGroup = (group) => {
        setGroups([group, ...groups])
    }

    const removeGroup = (groupId) => {
        setGroups(groups.filter(g => g.id !== groupId))
    }

    const updateGroupMetadata = (newGroupMetadata) => {
        setChosenGroup(newGroupMetadata)
        if (newGroupMetadata === undefined) {
            fetchGroupsMetadata()
        }
    }

    if (chosenGroup) {
        return (
            <GroupViewContainer
                user={user}
                groupMetadata={chosenGroup}
                onExit={() => setChosenGroup(undefined)}
                updateGroupMetadata={updateGroupMetadata}
                removeGroup={removeGroup}
            />
        )
    }

    return (
        <>
            <TopBar text={"JustCountIt"} onExit={logout} />
            <View style={styles.container}>
                <View style={styles.titleContainer}>
                    <Text style={styles.title}>
                        Twoje grupy
                    </Text>
                </View>
                <View style={styles.listContainer}>
                    <FlatList
                        data={groups}
                        renderItem={({ item }) => (
                            <GroupItem
                                key={item.id}
                                name={item.name}
                                description={item.description}
                                onClick={() => fetchSpecificGroup(item.id)}
                            />
                        )}
                    />
                </View>
            </View>
            <CreateGroupModal
                modalVisible={createGroupModalOpened}
                setModalVisible={isCreateGroupModalOpened}
                addGroup={addGroup}
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
