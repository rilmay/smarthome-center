let deviceAPI = Vue.resource("/devices{/id}");

function getIndex(id, list) {
    for (let i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('device-form', {
    props: ['devices', 'deviceUnderEdit'],
    data: function() {
        return {
            text: '',
            id: null
        }
    },
    watch: {
        deviceUnderEdit: function (newVal, oldVal) {
            this.text = newVal.name;
            this.id = newVal.id;
        }
    },
    template: '<div>' +
        '<input type="text" placeholder="enter device name"v-model="text"/>' +
        '<input type="button" value="save" @click="upsert"/>' +
        '</div>',
    methods: {
        upsert: function () {
            let device = { name: this.text };
            if (this.id) {
                deviceAPI.update({id: this.id}, device).then(result => result.json()).then(data => {
                    let index = getIndex(data.id, this.devices);
                    this.devices.splice(index, 1, data);
                });
                this.id = null;
            } else {
                deviceAPI.save({}, device).then(result => result.json()).then(data => this.devices.push(data));
            }
            this.text = '';
        }
    }
});

Vue.component('device-row', {
    props: ['device', 'editDevice', 'devices'],
    template: '<div>' +
                '<i>({{ device.id }})</i>  {{ device.name }}' +
                '<span style="position: absolute; right: 0">' +
                    '<input type="button" value="Edit" @click="edit"/>' +
                    '<input type="button" value="Delete" @click="del">' +
                '</span>' +
            '</div>',
    methods: {
        edit: function () {
            this.editDevice(this.device);
        },
        del: function () {
            deviceAPI.remove({id: this.device.id}).then(result =>{
                if (result.ok) {
                    this.devices.splice(this.devices.indexOf(this.device), 1)
                }
            })
        }

    }
});

Vue.component('devices-list', {
    props: ['devices'],
    data: function() {
        return {
            deviceUnderEdit: null
        }
    },
    template: '<div style="width: 300px; position: relative;">' +
        '<device-form :devices="devices" :deviceUnderEdit="deviceUnderEdit"></device-form>' +
        '<device-row v-for="device in devices" :key="device.id" :device="device" :editDevice="editDevice" :devices="devices"></device-row>' +
        '</div>',
    created: function () {
        deviceAPI.get().then(result => result.json()).then(data => data.forEach(device => this.devices.push(device)));
    },
    methods: {
        editDevice: function (device) {
            this.deviceUnderEdit = device;
        }
    }
});

let app = new Vue({
    el: '#app',
    template: '<devices-list :devices="devices"/>',
    data: {
        devices: []
    }
});