<template>
  <v-container class="victim">
    <div
      v-if="showLoader"
      style="
      position: fixed;
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background-color: rgba(0,0,0,0.5);
      z-index: 2;
      cursor: pointer;
    "
    >
    <v-progress-circular
      indeterminate
      color="amber"
      style="position: fixed;
      top: 50%;
      left: 50%;"
    ></v-progress-circular>
    </div>
    <v-row align="center">
      <v-col cols="12" sm="3"></v-col>
      <v-col cols="12" sm="6">
        <view-issues :issues="issues"></view-issues>
        <div align="center">
          <v-btn
            class="entrance-button"
            width="600px"
            height="50px"
            color="red"
            @click="addIssue()"
          >Add Issue</v-btn>
        </div>
        <add-issue v-if="isOpenAddIssueDialog" :closeAddIssuePopup="closeAddIssuePopup"></add-issue>
      </v-col>
      <v-col cols="12" sm="3"></v-col>
    </v-row>
  </v-container>
</template>

<script>
import AddIssue from "@/components/Victim/AddIssue";
import ViewIssues from "@/components/Victim/ViewIssues";
import { getIssuesBasedOnCurrentLocation } from "@/api/victim";

export default {
  name: "Victim",
  data() {
    return {
      isOpenAddIssueDialog: false,
      issues: [],
      showLoader: true
    };
  },
  components: {
    AddIssue,
    ViewIssues
  },
  methods: {
    addIssue() {
      this.isOpenAddIssueDialog = true;
    },
    closeAddIssuePopup () {
      this.isOpenAddIssueDialog = false
    }
  },
  mounted() {
    if (navigator && navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(position => {
        getIssuesBasedOnCurrentLocation(
          position.coords.latitude,
          position.coords.longitude
        ).then(res => {
          if (res["status"] === 200) {
            this.issues = res.data.issues;
            this.showLoader = false;
          }
        });
      });
    }
  }
};
</script>