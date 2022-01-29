import React from "react";
import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderWidth: 0,
        borderBottomWidth: 1,
        borderColor: '#BB86FC'
    },
    textContainer: {
        paddingVertical: 10,
    },
    title: {
        color: 'white',
        fontSize: 18,
    },
    description: {
        color: 'grey',
        fontSize: 15
    }
})