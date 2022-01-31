import { View, Text } from "react-native"
import * as Progress from 'react-native-progress';
import { styles } from "./BalanceChartStyles";

export const BalanceChart = ({ user, groupMetadata, groupBalanceMetadata }) => {
    const balances = Object.values(groupBalanceMetadata).map(m => m.balance)
    const maxBalance = Math.max(...balances) || 1
    const minBalance = Math.min(...balances) || 1

    return (
        <View style={styles.container}>
            {Object.values(groupBalanceMetadata).map(balanceMeta => {
                if (balanceMeta.balance < 0) {
                    const progress = balanceMeta.balance / minBalance
                    return (
                        <View key={balanceMeta.userId}>
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
                        </View>
                    )
                } else {
                    const progress = balanceMeta.balance / maxBalance
                    return (
                        <View key={balanceMeta.userId}>
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
                        </View>
                    )
                }
            })}
        </View>
    )
}
