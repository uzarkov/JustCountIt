import React from 'react';
import { Platform, TouchableOpacity, UIManager, Text, LayoutAnimation, StyleSheet, View } from "react-native";
import MaterialIcons from "react-native-vector-icons/MaterialIcons";

export const Accordion = ({ text, isOpen, onOpen, onClose, children }) => {
    if (Platform.OS === "android") {
        UIManager?.setLayoutAnimationEnabledExperimental(true);
    }

    return (
        <>
            <TouchableOpacity
                style={styles.heading}
                onPress={() => {
                    if (isOpen) {
                        onClose()
                    } else {
                        onOpen()
                    }
                    LayoutAnimation.configureNext(LayoutAnimation.Presets.easeInEaseOut)
                }}
            >
                <Text style={styles.text}>{text}</Text>
                <MaterialIcons
                    name={isOpen ? "expand-less" : "expand-more"}
                    color={'whitesmoke'}
                    size={30}
                />
            </TouchableOpacity>
            <View style={styles.stripe}></View>
            {isOpen && children}
        </>
    )
}

const styles = StyleSheet.create({
    heading: {
        alignItems: "center",
        flexDirection: "row",
        justifyContent: "space-between",
        paddingVertical: 10
    },
    text: {
        fontWeight: 'bold',
        fontSize: 20,
        color: 'white',
    },
    stripe: {
        alignSelf: 'center',
        width: '100%',
        height: 2,
        backgroundColor: '#BB86FC',
    },
})