import { View, Text, StyleSheet, Pressable } from "react-native"
import MaterialIcons from "react-native-vector-icons/MaterialIcons";
import MaterialCommunityIcons from "react-native-vector-icons/MaterialCommunityIcons";

export const MemberItem = ({ member, removingEnabled, onRemove }) => {
    const { name, role } = member;

    return (
        <View style={styles.outerContainer}>
            <View style={styles.container}>
                <MaterialIcons
                    name={"person"}
                    color={'whitesmoke'}
                    size={36}
                />
                <Text style={styles.text}>{`${name} (${parseRole(role)})`}</Text>
                {removingEnabled ?
                    <Pressable
                        onPress={() => onRemove()}
                    >
                        <MaterialCommunityIcons
                            name={"trash-can"}
                            color={'whitesmoke'}
                            size={36}
                        />
                    </Pressable>
                    : <MaterialCommunityIcons
                        name={"trash-can"}
                        color={'transparent'}
                        size={36}
                    />}

            </View>

            <View style={styles.stripe}></View>
        </View>
    )
}

const parseRole = (role) => {
    switch (role) {
        case "ORGANIZER": return "Organizator";
        case "PAYMASTER": return "Skarbnik";
        default: return "Członek grupy";
    }
}

const styles = StyleSheet.create({
    outerContainer: {
        marginHorizontal: 10,
        padding: 10,
    },
    container: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center'
    },
    nameContainer: {
        flexDirection: 'column'
    },
    text: {
        fontSize: 18,
        color: 'whitesmoke',
    },
    stripe: {
        alignSelf: 'center',
        width: '100%',
        height: 2,
        backgroundColor: '#454545',
    },
})