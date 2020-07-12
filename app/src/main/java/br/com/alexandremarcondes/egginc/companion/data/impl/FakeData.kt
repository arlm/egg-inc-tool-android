package br.com.alexandremarcondes.egginc.companion.data.impl

import br.com.alexandremarcondes.egginc.companion.data.model.*
import ei.Ei

val achievementInfo1: Ei.Backup.AchievementInfo = Ei.Backup.AchievementInfo.newBuilder()
    .setAchieved(true)
    .setId("achievement1")
    .build()

val achievementInfo2: Ei.Backup.AchievementInfo = Ei.Backup.AchievementInfo.newBuilder()
    .setAchieved(true)
    .setId("achievement2")
    .build()

val achievementInfo3: Ei.Backup.AchievementInfo = Ei.Backup.AchievementInfo.newBuilder()
    .setAchieved(true)
    .setId("achievement3")
    .build()

val boostInfo1: Ei.Backup.OwnedBoost = Ei.Backup.OwnedBoost.newBuilder()
    .setBoostId("boost1")
    .setCount(1)
    .build()

val boostInfo2: Ei.Backup.OwnedBoost = Ei.Backup.OwnedBoost.newBuilder()
    .setBoostId("boost2")
    .setCount(2)
    .build()

val boostInfo3: Ei.Backup.OwnedBoost = Ei.Backup.OwnedBoost.newBuilder()
    .setBoostId("boost3")
    .setCount(3)
    .build()

val activeBoost1: Ei.Backup.ActiveBoost = Ei.Backup.ActiveBoost.newBuilder()
    .setBoostId("boost1")
    .setReferenceValue(2.0)
    .setTimeRemaining(3.0)
    .build()

val activeBoost2: Ei.Backup.ActiveBoost = Ei.Backup.ActiveBoost.newBuilder()
    .setBoostId("boost2")
    .setReferenceValue(2.0)
    .setTimeRemaining(3.0)
    .build()

val researchItem1: Ei.Backup.ResearchItem = Ei.Backup.ResearchItem.newBuilder()
    .setId("prophecy_bonus")
    .setLevel(5)
    .build()

val researchItem2: Ei.Backup.ResearchItem = Ei.Backup.ResearchItem.newBuilder()
    .setId("soul_eggs")
    .setLevel(140)
    .build()

val researchItem3: Ei.Backup.ResearchItem = Ei.Backup.ResearchItem.newBuilder()
    .setId("research3")
    .setLevel(3)
    .build()

val simulationHome: Ei.Backup.Simulation = Ei.Backup.Simulation.newBuilder()
    .setEggType(Ei.Egg.NEBULA)
    .setFarmType(Ei.FarmType.HOME)
    .setCashEarned(5.0)
    .setCashSpent(3.0)
    .setUnclaimedCash(2.0)
    .setLastStepTime(4.0)
    .setNumChickens(6)
    .setNumChickensRunning(4)
    .setNumChickensUnsettled(2)
    .setEggsLaid(3.0)
    .setEggsPaidFor(2.0)
    .setSilosOwned(12)
    .addHabs(1)
    .addHabs(2)
    .addHabs(3)
    .addHabs(4)
    .addHabPopulation(1)
    .addHabPopulation(2)
    .addHabPopulation(3)
    .addHabPopulation(4)
    .addHabPopulationIndound(1)
    .addHabPopulationIndound(2)
    .addHabPopulationIndound(3)
    .addHabPopulationIndound(4)
    .addHabIncubatorPopuplation(1.0)
    .addHabIncubatorPopuplation(2.0)
    .addHabIncubatorPopuplation(3.0)
    .addHabIncubatorPopuplation(4.0)
    .setHatcheryPopulation(10.0)
    .addVehicles(1)
    .addVehicles(2)
    .addVehicles(3)
    .addVehicles(4)
    .addTrainLength(1)
    .addTrainLength(2)
    .addTrainLength(3)
    .addTrainLength(4)
    .addCommonResearch(researchItem1)
    .addCommonResearch(researchItem2)
    .addCommonResearch(researchItem3)
    .addActiveBoosts(activeBoost1)
    .addActiveBoosts(activeBoost2)
    .setTimeCheatsDetected(2)
    .setTimeCheatDebt(2.0)
    .setBoostTokensReceived(10)
    .setBoostTokensSpent(4)
    .setBoostTokensGiven(2)
    .setBoostTokensToGive(4)
    .build()

val simulationContract1: Ei.Backup.Simulation = Ei.Backup.Simulation.newBuilder()
    .setEggType(Ei.Egg.UNIVERSE)
    .setFarmType(Ei.FarmType.CONTRACT)
    .setContractId("contract1")
    .setCashEarned(5.0)
    .setCashSpent(3.0)
    .setUnclaimedCash(2.0)
    .setLastStepTime(4.0)
    .setNumChickens(6)
    .setNumChickensRunning(4)
    .setNumChickensUnsettled(2)
    .setEggsLaid(3.0)
    .setEggsPaidFor(2.0)
    .setSilosOwned(12)
    .addHabs(1)
    .addHabs(2)
    .addHabs(3)
    .addHabs(4)
    .addHabPopulation(1)
    .addHabPopulation(2)
    .addHabPopulation(3)
    .addHabPopulation(4)
    .addHabPopulationIndound(1)
    .addHabPopulationIndound(2)
    .addHabPopulationIndound(3)
    .addHabPopulationIndound(4)
    .addHabIncubatorPopuplation(1.0)
    .addHabIncubatorPopuplation(2.0)
    .addHabIncubatorPopuplation(3.0)
    .addHabIncubatorPopuplation(4.0)
    .setHatcheryPopulation(10.0)
    .addVehicles(1)
    .addVehicles(2)
    .addVehicles(3)
    .addVehicles(4)
    .addTrainLength(1)
    .addTrainLength(2)
    .addTrainLength(3)
    .addTrainLength(4)
    .addCommonResearch(researchItem1)
    .addCommonResearch(researchItem2)
    .addCommonResearch(researchItem3)
    .addActiveBoosts(activeBoost1)
    .addActiveBoosts(activeBoost2)
    .setTimeCheatsDetected(2)
    .setTimeCheatDebt(2.0)
    .setBoostTokensReceived(10)
    .setBoostTokensSpent(4)
    .setBoostTokensGiven(2)
    .setBoostTokensToGive(4)
    .build()

val simulationContract2: Ei.Backup.Simulation = Ei.Backup.Simulation.newBuilder()
    .setEggType(Ei.Egg.MEDICAL)
    .setFarmType(Ei.FarmType.CONTRACT)
    .setContractId("contract2")
    .setCashEarned(5.0)
    .setCashSpent(3.0)
    .setUnclaimedCash(2.0)
    .setLastStepTime(4.0)
    .setNumChickens(6)
    .setNumChickensRunning(4)
    .setNumChickensUnsettled(2)
    .setEggsLaid(3.0)
    .setEggsPaidFor(2.0)
    .setSilosOwned(12)
    .addHabs(1)
    .addHabs(2)
    .addHabs(3)
    .addHabs(4)
    .addHabPopulation(1)
    .addHabPopulation(2)
    .addHabPopulation(3)
    .addHabPopulation(4)
    .addHabPopulationIndound(1)
    .addHabPopulationIndound(2)
    .addHabPopulationIndound(3)
    .addHabPopulationIndound(4)
    .addHabIncubatorPopuplation(1.0)
    .addHabIncubatorPopuplation(2.0)
    .addHabIncubatorPopuplation(3.0)
    .addHabIncubatorPopuplation(4.0)
    .setHatcheryPopulation(10.0)
    .addVehicles(1)
    .addVehicles(2)
    .addVehicles(3)
    .addVehicles(4)
    .addTrainLength(1)
    .addTrainLength(2)
    .addTrainLength(3)
    .addTrainLength(4)
    .addCommonResearch(researchItem1)
    .addCommonResearch(researchItem2)
    .addCommonResearch(researchItem3)
    .addActiveBoosts(activeBoost1)
    .addActiveBoosts(activeBoost2)
    .setTimeCheatsDetected(2)
    .setTimeCheatDebt(2.0)
    .setBoostTokensReceived(10)
    .setBoostTokensSpent(4)
    .setBoostTokensGiven(2)
    .setBoostTokensToGive(4)
    .build()

val simulationContract3: Ei.Backup.Simulation = Ei.Backup.Simulation.newBuilder()
    .setEggType(Ei.Egg.ANTIMATTER)
    .setFarmType(Ei.FarmType.CONTRACT)
    .setContractId("contract3")
    .setCashEarned(5.0)
    .setCashSpent(3.0)
    .setUnclaimedCash(2.0)
    .setLastStepTime(4.0)
    .setNumChickens(6)
    .setNumChickensRunning(4)
    .setNumChickensUnsettled(2)
    .setEggsLaid(3.0)
    .setEggsPaidFor(2.0)
    .setSilosOwned(12)
    .addHabs(1)
    .addHabs(2)
    .addHabs(3)
    .addHabs(4)
    .addHabPopulation(1)
    .addHabPopulation(2)
    .addHabPopulation(3)
    .addHabPopulation(4)
    .addHabPopulationIndound(1)
    .addHabPopulationIndound(2)
    .addHabPopulationIndound(3)
    .addHabPopulationIndound(4)
    .addHabIncubatorPopuplation(1.0)
    .addHabIncubatorPopuplation(2.0)
    .addHabIncubatorPopuplation(3.0)
    .addHabIncubatorPopuplation(4.0)
    .setHatcheryPopulation(10.0)
    .addVehicles(1)
    .addVehicles(2)
    .addVehicles(3)
    .addVehicles(4)
    .addTrainLength(1)
    .addTrainLength(2)
    .addTrainLength(3)
    .addTrainLength(4)
    .addCommonResearch(researchItem1)
    .addCommonResearch(researchItem2)
    .addCommonResearch(researchItem3)
    .addActiveBoosts(activeBoost1)
    .addActiveBoosts(activeBoost2)
    .setTimeCheatsDetected(2)
    .setTimeCheatDebt(2.0)
    .setBoostTokensReceived(10)
    .setBoostTokensSpent(4)
    .setBoostTokensGiven(2)
    .setBoostTokensToGive(4)
    .build()

val simulationContract4: Ei.Backup.Simulation = Ei.Backup.Simulation.newBuilder()
    .setEggType(Ei.Egg.PUMPKIN)
    .setFarmType(Ei.FarmType.CONTRACT)
    .setContractId("contract4")
    .setCashEarned(5.0)
    .setCashSpent(3.0)
    .setUnclaimedCash(2.0)
    .setLastStepTime(4.0)
    .setNumChickens(6)
    .setNumChickensRunning(4)
    .setNumChickensUnsettled(2)
    .setEggsLaid(3.0)
    .setEggsPaidFor(2.0)
    .setSilosOwned(12)
    .addHabs(1)
    .addHabs(2)
    .addHabs(3)
    .addHabs(4)
    .addHabPopulation(1)
    .addHabPopulation(2)
    .addHabPopulation(3)
    .addHabPopulation(4)
    .addHabPopulationIndound(1)
    .addHabPopulationIndound(2)
    .addHabPopulationIndound(3)
    .addHabPopulationIndound(4)
    .addHabIncubatorPopuplation(1.0)
    .addHabIncubatorPopuplation(2.0)
    .addHabIncubatorPopuplation(3.0)
    .addHabIncubatorPopuplation(4.0)
    .setHatcheryPopulation(10.0)
    .addVehicles(1)
    .addVehicles(2)
    .addVehicles(3)
    .addVehicles(4)
    .addTrainLength(1)
    .addTrainLength(2)
    .addTrainLength(3)
    .addTrainLength(4)
    .addCommonResearch(researchItem1)
    .addCommonResearch(researchItem2)
    .addCommonResearch(researchItem3)
    .addActiveBoosts(activeBoost1)
    .addActiveBoosts(activeBoost2)
    .setTimeCheatsDetected(2)
    .setTimeCheatDebt(2.0)
    .setBoostTokensReceived(10)
    .setBoostTokensSpent(4)
    .setBoostTokensGiven(2)
    .setBoostTokensToGive(4)
    .build()

val game: Ei.Backup.Game = Ei.Backup.Game.newBuilder()
    .addAchievements(achievementInfo1)
    .addAchievements(achievementInfo2)
    .addAchievements(achievementInfo3)
    .addBoosts(boostInfo1)
    .addBoosts(boostInfo2)
    .addBoosts(boostInfo3)
    .addEpicResearch(researchItem1)
    .addEpicResearch(researchItem2)
    .addEpicResearch(researchItem3)
    .addEggMedalLevel(1)
    .addEggMedalLevel(2)
    .addEggMedalLevel(3)
    .addMaxFarmSizeReached(1)
    .addMaxFarmSizeReached(1)
    .addMaxFarmSizeReached(1)
    .setCurrentFarm(Ei.Egg.ANTIMATTER_VALUE)
    .setCurrentMultiplier(2.0)
    .setCurrentMultiplierExpiration(2.0)
    .setEggsOfProphecy(146)
    .setSoulEggsD(469864648848166340.0)
    .setUnclaimedSoulEggs(2)
    .setUnclaimedSoulEggsD(2.0)
    .setUnclaimedEggsOfProphecy(2)
    .setPrestigeCashEarned(2.0)
    .setPrestigeSoulBoostCash(3.0)
    .setLifetimeCashEarned(44.0)
    .setPiggyBank(3)
    .setPermitLevel(3)
    .setHyperloopStation(true)
    .setTotalTimeCheatsDetected(4)
    .build()

val stats: Ei.Backup.Stats = Ei.Backup.Stats.newBuilder()
    .setBoostsUsed(2)
    .setDroneTakedowns(3)
    .setDroneTakedownsElite(4)
    .addEggTotals(2000.0)
    .addEggTotals(3000.0)
    .addEggTotals(4000.0)
    .setIapPacksPurchased(5)
    .setLostPiggyIncrements(1)
    .setNumPiggyBreaks(6)
    .setPiggyFoundFull(true)
    .setNumPrestiges(7)
    .setRefillUses(8)
    .setTimePiggyFilledRealtime(6.0)
    .setTimePiggyFullGametime(7.0)
    .setUnlimitedChickensUses(8)
    .setVideoDoublerUses(4)
    .setWarp1Uses(1)
    .setWarp8Uses(2)
    .build()

val goal1: Ei.Contract.Goal = Ei.Contract.Goal.newBuilder()
    .setType(Ei.GoalType.EGGS_LAID)
    .setTargetAmount(100.0)
    .setTargetSoulEggs(5.0)
    .setRewardType(Ei.RewardType.SOUL_EGGS)
    .setRewardAmount(10.0)
    .setRewardSubType("soulegg")
    .build()

val goal2: Ei.Contract.Goal = Ei.Contract.Goal.newBuilder()
    .setType(Ei.GoalType.EGGS_LAID)
    .setTargetAmount(100.0)
    .setTargetSoulEggs(5.0)
    .setRewardType(Ei.RewardType.CASH)
    .setRewardAmount(10.0)
    .setRewardSubType("soulegg")
    .build()

val goal3: Ei.Contract.Goal = Ei.Contract.Goal.newBuilder()
    .setType(Ei.GoalType.EGGS_LAID)
    .setTargetAmount(100.0)
    .setTargetSoulEggs(5.0)
    .setRewardType(Ei.RewardType.EPIC_RESEARCH_ITEM)
    .setRewardAmount(10.0)
    .setRewardSubType("soulegg")
    .build()

val contract1: Ei.Contract = Ei.Contract.newBuilder()
    .setIdentifier("contract1")
    .setName("contract 1")
    .setDescription("contract 1 description")
    .setEgg(Ei.Egg.ANTIMATTER)
    .addGoals(goal1)
    .addGoals(goal2)
    .addGoals(goal3)
    .setCoopAllowed(true)
    .setMaxCoopSize(10)
    .setMaxBoosts(4)
    .setMaxSoulEggs(4.0)
    .setExpirationTime(4.0)
    .setLengthSeconds(10.0)
    .setMinClientVersion(1)
    .build()

val contract2: Ei.Contract = Ei.Contract.newBuilder()
    .setIdentifier("contract2")
    .setName("contract 2")
    .setDescription("contract 2 description")
    .setEgg(Ei.Egg.MEDICAL)
    .addGoals(goal1)
    .addGoals(goal2)
    .addGoals(goal3)
    .setCoopAllowed(true)
    .setMaxCoopSize(4)
    .setMaxBoosts(4)
    .setMaxSoulEggs(4.0)
    .setExpirationTime(4.0)
    .setLengthSeconds(10.0)
    .setMinClientVersion(1)
    .build()

val contract3: Ei.Contract = Ei.Contract.newBuilder()
    .setIdentifier("contract3")
    .setName("contract 3")
    .setDescription("contract 3 description")
    .setEgg(Ei.Egg.PUMPKIN)
    .addGoals(goal1)
    .addGoals(goal2)
    .addGoals(goal3)
    .setCoopAllowed(false)
    .setMaxCoopSize(0)
    .setMaxBoosts(4)
    .setMaxSoulEggs(4.0)
    .setExpirationTime(4.0)
    .setLengthSeconds(10.0)
    .setMinClientVersion(1)
    .build()

val localContract1: Ei.LocalContract = Ei.LocalContract.newBuilder()
    .setContract(contract1)
    .setCoopIdentifier("coop1")
    .setAccepted(true)
    .setTimeAccepted(4.0)
    .setNew(true)
    .setCancelled(false)
    .setCoopSharedEndTime(4.0)
    .setCoopGracePeriodEndTime(2.0)
    .setCoopContributionFinalized(false)
    .setCoopLastUploadedContribution(2.0)
    .setLastAmountWhenRewardGiven(2.0)
    .setBoostsUsed(2)
    .build()

val localContract2: Ei.LocalContract = Ei.LocalContract.newBuilder()
    .setContract(contract2)
    .setAccepted(false)
    .setNew(false)
    .setCancelled(false)
    .build()

val localContract3: Ei.LocalContract = Ei.LocalContract.newBuilder()
    .setContract(contract3)
    .setAccepted(true)
    .setTimeAccepted(4.0)
    .setNew(true)
    .setCancelled(false)
    .setLastAmountWhenRewardGiven(2.0)
    .setBoostsUsed(2)
    .build()

val myContracts: Ei.MyContracts = Ei.MyContracts.newBuilder()
    .addContractIdsSeen("contract1")
    .addContractIdsSeen("contract2")
    .addContractIdsSeen("contract3")
    .addContracts(localContract1)
    .addContracts(localContract2)
    .addContracts(localContract3)
    .addArchive(localContract1)
    .build()

val backup1: Ei.Backup = Ei.Backup.newBuilder()
    .setUserName("alex1")
    .setUserId("alex1_id")
    .setApproxTime(3.0)
    .setChecksum(4)
    .setVersion(55)
    .setStats(stats)
    .setGame(game)
    .setSim(simulationHome)
    .addFarms(simulationContract1)
    .addFarms(simulationContract2)
    .addFarms(simulationContract3)
    .addFarms(simulationContract4)
    .setContracts(myContracts)
    .build()

val backup2: Ei.Backup = Ei.Backup.newBuilder()
    .setUserName("alex2")
    .setUserId("alex2_id")
    .setApproxTime(3.0)
    .setChecksum(4)
    .setVersion(55)
    .setStats(stats)
    .setGame(game)
    .setSim(simulationHome)
    .addFarms(simulationContract1)
    .addFarms(simulationContract2)
    .addFarms(simulationContract3)
    .addFarms(simulationContract4)
    .setContracts(myContracts)
    .build()

val backup3: Ei.Backup = Ei.Backup.newBuilder()
    .setUserName("alex3")
    .setUserId("alex3_id")
    .setApproxTime(3.0)
    .setChecksum(4)
    .setVersion(55)
    .setStats(stats)
    .setGame(game)
    .setSim(simulationHome)
    .addFarms(simulationContract1)
    .addFarms(simulationContract2)
    .addFarms(simulationContract3)
    .addFarms(simulationContract4)
    .setContracts(myContracts)
    .build()

val backup4: Ei.Backup = Ei.Backup.newBuilder()
    .setUserName("alex4")
    .setUserId("alex4_id")
    .setApproxTime(3.0)
    .setChecksum(4)
    .setVersion(55)
    .setStats(stats)
    .setGame(game)
    .build()

val backup5: Ei.Backup = Ei.Backup.newBuilder()
    .setUserName("alex5")
    .setUserId("alex5_id")
    .setApproxTime(3.0)
    .setChecksum(4)
    .setVersion(55)
    .setStats(stats)
    .setGame(game)
    .setSim(simulationHome)
    .addFarms(simulationContract1)
    .addFarms(simulationContract2)
    .addFarms(simulationContract3)
    .addFarms(simulationContract4)
    .setContracts(myContracts)
    .build()

val fakeGoal1 = ContractGoal(
    type = GoalType.EGGS_LAID,
    targetAmount = 100.0,
    targetSoulEggs = 5.0,
    rewardType = RewardType.SOUL_EGGS,
    rewardAmount = 10.0,
    rewardSubType = "soulegg"
)

val fakeGoal2 = ContractGoal(
    type = GoalType.EGGS_LAID,
    targetAmount = 100.0,
    targetSoulEggs = 5.0,
    rewardType = RewardType.CASH,
    rewardAmount = 10.0,
    rewardSubType = "soulegg"
)

val fakeGoal3 = ContractGoal(
    type = GoalType.EGGS_LAID,
    targetAmount = 100.0,
    targetSoulEggs = 5.0,
    rewardType = RewardType.EPIC_RESEARCH_ITEM,
    rewardAmount = 10.0,
    rewardSubType = "soulegg"
)

val fakeContract1 = Contract(
    identifier = "contract1",
    name = "contract 1",
    description = "contract 1 description",
    egg = Egg.ANTIMATTER,
    goals = listOf(fakeGoal1, fakeGoal2, fakeGoal3),
    coopAllowed = true,
    maxCoopSize = 10,
    maxBoosts = 4,
    maxSoulEggs = 4.0,
    expirationTime = 1595858460.1665452,
    lengthSeconds = 604800.0,
    minClientVersion = 26
)

val user1 = User(backup1)
val user2 = User(backup2)
val user3 = User(backup3)
val user4 = User(backup4)
val user5 = User(backup5)

val myContract1 = Contract().applyFrom(contract1)
val myContract2 = Contract().applyFrom(contract2)
val myContract3 = Contract().applyFrom(contract3)

val fakeUsers: List<User> =
    listOf(
        user1,
        user2,
        user3,
        user4,
        user5
    )

val fakeContracts: List<Contract> =
    listOf(
        myContract1,
        myContract2,
        myContract3
    )