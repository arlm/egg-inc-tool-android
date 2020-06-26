package br.com.alexandremarcondes.egginc.companion.data.impl


val user1 = ei.Ei.DeviceInfo.newBuilder()
    .setDeviceId("55db18283aca1")
    .setDeviceName("alex1")
    .build()

val user2 = ei.Ei.DeviceInfo.newBuilder()
    .setDeviceId("55db18283aca2")
    .setDeviceName("alex2")
    .build()

val user3 = ei.Ei.DeviceInfo.newBuilder()
    .setDeviceId("55db18283aca3")
    .setDeviceName("alex3")
    .build()

val user4 = ei.Ei.DeviceInfo.newBuilder()
    .setDeviceId("55db18283aca4")
    .setDeviceName("alex4")
    .build()

val user5 = ei.Ei.DeviceInfo.newBuilder()
    .setDeviceId("55db18283aca5")
    .setDeviceName("alex5")
    .build()

val fakeUsers: List<ei.Ei.DeviceInfo> =
    listOf(
        user1,
        user2,
        user3,
        user4,
        user5
    )

