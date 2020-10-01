<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">User Authentication</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field label="Name*" required v-model="userName"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Phone Number*" required v-model="phoneNumber"></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <div class="flex-grow-1"></div>
          <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
          <v-btn color="blue darken-1" text @click="createUser()">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import {
  createVolunteer as createVolunteerAPI,
  acknowledgeIssue
} from "@/api/volunteer";
import cookiemixin from "@/components/Shared/cookiemixin";
import { createDonor } from "@/api/donor";

export default {
  name: "AuthenticationDialog",
  props: ["userType"],
  mixins: [cookiemixin],
  data() {
    return {
      dialog: true,
      phoneNumber: "",
      userName: ""
    };
  },
  methods: {
    createUser() {
      if (this.userType === "volunteer") {
        var data = {
          name: this.userName,
          phoneNo: this.phoneNumber
        };
        createVolunteerAPI(data).then(res => {
          console.log(res.data);
          this.setCookie("lynkUser", this.phoneNumber);
          this.setCookie("lynkUserId", res.data.insertedId);
          location.reload();
        });
      } else {
        var data = {
          name: this.userName,
          phoneNo: this.phoneNumber
        };
        if (navigator && navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(position => {
            data["lat"] = position.coords.latitude;
            data["long"] = position.coords.longitude;
            createDonor(data).then(res => {
              console.log(res.data);
              this.setCookie("lynkUser", this.phoneNumber);
              this.setCookie("lynkUserId", res.data.insertedId);
              location.reload();
            });
          });
        }
      }
    }
  }
};
</script>