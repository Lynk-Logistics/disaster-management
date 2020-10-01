<template>
  <v-container class="victim">
    <v-row>
      <v-col cols="12" sm="6">
        <v-tabs fixed-tabs background-color="yellow" dark v-model="tabs">
          <v-tab color="black" href="#issues-map">
            <span style="color:black">Issues Map</span>
          </v-tab>
          <v-tab color="black" href="#donor-map">
            <span style="color:black">Donors Map</span>
          </v-tab>
        </v-tabs>
        <v-tabs-items v-model="tabs">
          <v-tab-item key="issues-map" value="issues-map">
            <issue-map :locations="locations"></issue-map>
          </v-tab-item>
          <v-tab-item key="donor-map" value="donor-map">
            <donor-map :locations="donorLocations"></donor-map>
          </v-tab-item>
        </v-tabs-items>
      </v-col>
      <v-col cols="12" sm="6">
        <v-tabs fixed-tabs background-color="yellow" dark v-model="tab">
          <v-tab color="black" href="#all-issues">
            <span style="color:black">All issues</span>
          </v-tab>
          <v-tab color="black" href="#my-issues">
            <span style="color:black">Acknowledge issues</span>
          </v-tab>
        </v-tabs>
        <v-tabs-items v-model="tab">
          <v-tab-item key="all-issues" value="all-issues">
            <view-issues buttonText="Acknowledge" :issues="nonAcknowledgedIssues"></view-issues>
          </v-tab-item>
          <v-tab-item key="my-issues" value="my-issues">
            <view-issues buttonText="Resolve" :issues="acknowledgedIssues"></view-issues>
          </v-tab-item>
        </v-tabs-items>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import IssueMap from "@/components/Volunteer/IssueMap";
import DonorMap from "@/components/Volunteer/DonorMap";
import ViewIssues from "@/components/Victim/ViewIssues.vue";
import {
  getAllIssues,
  getNonAcknowledgedIssues,
  getAcknowledgedIssues
} from "@/api/volunteer";
import { getAllDonors } from "@/api/donor";
import cookiemixin from "@/components/Shared/cookiemixin";

export default {
  name: "Volunteer",
  mixins: [cookiemixin],
  data() {
    return {
      tab: null,
      tabs: null,
      issues: [],
      locations: [],
      donors: [],
      donorLocations: [],
      nonAcknowledgedIssues: [],
      acknowledgedIssues: []
    };
  },
  components: {
    IssueMap,
    ViewIssues,
    DonorMap
  },
  mounted() {
    let mapsScript = document.createElement("script");
    let clusterScript = document.createElement("script");

    mapsScript.setAttribute(
      "src",
      "https://maps.googleapis.com/maps/api/js?key=AIzaSyAWWKxwv_xbsvhUjpbJzhTxR8ewkxByC9I"
    );
    clusterScript.setAttribute(
      "src",
      "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js"
    );

    document.head.appendChild(mapsScript);
    document.head.appendChild(clusterScript);

    if (this.getCookie("lynkUserId")) {
      getAcknowledgedIssues(this.getCookie("lynkUserId")).then(res => {
        if (res.data) {
          this.acknowledgedIssues = res.data.issues;
        }
      });
    }

    getAllIssues(this.getCookie("lynkUserId")).then(res => {
      if (res.data) {
        this.issues = res.data.issues;
        var locations = [];
        res.data.issues.forEach(issue => {
          var location = {
            longitude: issue.location.coordinates[0],
            latitude: issue.location.coordinates[1],
            issueId: issue.id
          };
          locations.push(location);
          this.locations = locations;
        });
      }
    });

    getNonAcknowledgedIssues(this.getCookie("lynkUserId")).then(res => {
      if (res.data) {
        this.nonAcknowledgedIssues = res.data.issues;
      }
    });

    getAllDonors().then(res => {
      if (res.data) {
        this.donors = res.data.donors;
        var locations = [];
        this.donors.forEach(donor => {
          var location = {
            longitude: donor.location.coordinates[0],
            latitude: donor.location.coordinates[1],
            donarId: donor.id
          };
          locations.push(location);
          this.donorLocations = locations;
        });
      }
    });
  }
};
</script>