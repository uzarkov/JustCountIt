import React from "react";
import {Pressable, Text, View} from "react-native";
import {styles} from "./GroupItemStyles";
import MaterialIcons from 'react-native-vector-icons/MaterialIcons';

export const GroupItem = ({name, description, onClick}) => {
    return (
        <Pressable
            style={styles.container}
            onPress={onClick}
        >
            <View style={styles.textContainer}>
                <Text style={styles.title}>
                    {name}
                </Text>
                <Text style={styles.description}>
                    {description}
                </Text>
            </View>
            <MaterialIcons
                name={"arrow-forward-ios"}
                color={"#BB86FC"}
                size={20}
            />
        </Pressable>
    )
}