import React from 'react';
import { View, Text, Pressable } from 'react-native';
import MaterialIcons from "react-native-vector-icons/MaterialIcons";
import { styles } from './TopBarStyles';

export const TopBar = ({ text, onExit }) => {
    return (
        <View style={styles.container}>
            <Pressable>
                <MaterialIcons
                    name={"logout"}
                    color={"transparent"}
                    size={30}
                />
            </Pressable>
            <Text style={styles.title}>{text}</Text>
            <Pressable onPress={() => onExit()}>
                <MaterialIcons
                    name={"logout"}
                    color={"#FFFFFF"}
                    size={30}
                />
            </Pressable>
        </View>
    )
}