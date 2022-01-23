import { useState } from "react"
import { View } from "react-native"
import { TopBar } from "../../components/top-bar/TopBar"
import { BalanceView } from "./balance/BalanceView"
import { ExpendituresView } from "./expenditures/ExpendituresView"
import { GroupInfoView } from "./group-info/GroupInfoView"
import { GroupNavigation, ROUTES } from "./GroupNavigation"
import { styles } from "./GroupViewContainerStyles"

export const GroupViewContainer = ({ groupMetadata, onExit }) => {
    const [currentView, setCurrentView] = useState(ROUTES.EXPENDITURES)

    const getCurrentViewComponent = () => {
        switch (currentView) {
            case ROUTES.BALANCE:
                return (
                    <BalanceView />
                )
            case ROUTES.EXPENDITURES:
                return (
                    <ExpendituresView />
                )
            case ROUTES.GROUP_INFO:
                return (
                    <GroupInfoView />
                )
            default:
                throw new Error("You shouldn't be here")
        }
    }

    return (
        <View style={styles.container}>
            <TopBar text={groupMetadata.name} onExit={onExit} />
            <GroupNavigation
                currentView={currentView}
                onClicks={{
                    [`${ROUTES.BALANCE}`]: () => setCurrentView(ROUTES.BALANCE),
                    [`${ROUTES.EXPENDITURES}`]: () => setCurrentView(ROUTES.EXPENDITURES),
                    [`${ROUTES.GROUP_INFO}`]: () => setCurrentView(ROUTES.GROUP_INFO),
                }}
            />
            {getCurrentViewComponent()}
        </View>
    )
}