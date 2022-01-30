import { useEffect, useState } from "react"
import { View, Text, StyleSheet } from "react-native"
import * as Progress from 'react-native-progress';
import { styles } from "./BalanceChartStyles";
import { doGet } from "../../../utils/fetchUtils"

export const BalanceChart = ({ user, groupMetadata }) => {
    const [groupBalanceMetadata, setGroupBalanceMetadata] = useState([])
    const fetchGroupBalance = () => {
        doGet(`/api/balance/${groupMetadata.id}`)
            .then(response => response.json())
            .then(json => setGroupBalanceMetadata(json))
            .catch(err => console.log(err.message))
    }

    useEffect(() => {
        fetchGroupBalance()
    }, [])


    const balances = Object.values(groupBalanceMetadata).map(m => m.balance)
    const maxBalance = Math.max(...balances) || 1
    const minBalance = Math.min(...balances) || 1

    return (
        <View style={styles.container}>
            {Object.values(groupBalanceMetadata).map(balanceMeta => {
                if (balanceMeta.balance < 0) {
                    const progress = balanceMeta.balance / minBalance
                    return (
                        <>
                            <View style={styles.rowContainer}>
                                <Progress.Bar
                                    animated={false}
                                    style={styles.leftProgressBar}
                                    progress={progress}
                                    width={null}
                                    height={24}
                                    borderColor={'transparent'}
                                    color={'#e04744'}
                                >
                                    <Text style={styles.leftProgressBarText}>
                                        {`${balanceMeta.balance.toFixed(2)} ${groupMetadata.currency.symbol}`}
                                    </Text>
                                </Progress.Bar>
                                <Text style={styles.text}>
                                    {`${balanceMeta.name} ${balanceMeta.userId === user.id ? '(Ja)' : ''}`}
                                </Text>
                            </View>
                            <View style={styles.stripe}></View>
                        </>

                    )
                } else {
                    const progress = balanceMeta.balance / maxBalance
                    return (
                        <>
                            <View style={styles.rowContainer}>
                                <Text style={[styles.text, { textAlign: 'right' }]}>
                                    {`${balanceMeta.name} ${balanceMeta.userId === user.id ? '(Ja)' : ''}`}
                                </Text>
                                <Progress.Bar
                                    animated={false}
                                    style={styles.rightProgressBar}
                                    progress={progress}
                                    width={null}
                                    height={24}
                                    borderColor={'transparent'}
                                    color={'#2ca82c'}
                                >
                                    <Text style={styles.rightProgressBarText}>
                                        {`${balanceMeta.balance.toFixed(2)} ${groupMetadata.currency.symbol}`}
                                    </Text>
                                </Progress.Bar>
                            </View>
                            <View style={styles.stripe}></View>
                        </>
                    )
                }
            })}
        </View>
    )
}

// const sampleGroupBalanceMetadata = {
//     1: {
//         userId: 1,
//         name: "Andrzej",
//         balance: 132,
//     },
//     2: {
//         userId: 2,
//         name: "Julia",
//         balance: 32.46,
//     },
//     3: {
//         userId: 3,
//         name: "Robert",
//         balance: -23.55,
//     },
//     4: {
//         userId: 104,
//         name: "Krzysztof",
//         balance: -1.55,
//     },
    // 105: {
    //     userId: 105,
    //     name: "Tomasz",
    //     balance: 28.55,
    // },
    // 106: {
    //     userId: 106,
    //     name: "Ania",
    //     balance: 0,
    // },
    // 107: {
    //     userId: 107,
    //     name: "Michał",
    //     balance: 123.55,
    // },
    // 108: {
    //     userId: 108,
    //     name: "Kuba",
    //     balance: -43.55,
    // },
    // 109: {
    //     userId: 109,
    //     name: "Kuba",
    //     balance: -43.55,
    // },
    // 110: {
    //     userId: 110,
    //     name: "Kuba",
    //     balance: -43.55,
    // },
    // 111: {
    //     userId: 111,
    //     name: "Kuba",
    //     balance: -43.55,
    // },
    // 112: {
    //     userId: 112,
    //     name: "Kuba",
    //     balance: -43.55,
    // },
    // 113: {
    //     userId: 113,
    //     name: "Kuba",
    //     balance: -43.55,
    // },
    // 114: {
    //     userId: 114,
    //     name: "Kuba",
    //     balance: -43.55,
    // },
    // 115: {
    //     userId: 115,
    //     name: "Kuba",
    //     balance: -43.55,
    // },
    // 116: {
    //     userId: 116,
    //     name: "Kuba",
    //     balance: -43.55,
    // },
    // 118: {
    //     userId: 117,
    //     name: "Kuba",
    //     balance: -43.55,
    // },
    // 118: {
    //     userId: 118,
    //     name: "Kuba",
    //     balance: -43.55,
    // },
//}
