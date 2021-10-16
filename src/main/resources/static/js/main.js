let deviceAPI = Vue.resource("/devices{/id}");


Vue.component('device-row', {
    props: ['device'],
    template: '<div><i>{{ device.id }} {{ device.name }}</i></div>'
});

Vue.component('devices-list', {
    props: ['devices'],
    template: '<div><device-row v-for="device in devices" :key="device.id" :device="device"></device-row></div>',
    created: function () {
        deviceAPI.get().then(result => result.json()).then(data => data.forEach(device => this.devices.push(device)));
    }
});

var app = new Vue({
    el: '#app',
    template: '<devices-list :devices="devices"/>',
    data: {
        devices: []
    }
})