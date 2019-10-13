<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">Issue details</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="8">
                <v-list-item-title color="black">Title: {{issue.name}}</v-list-item-title>
                <v-list-item-subtitle class="pt-1">No. of people: {{issue.noOfPeople}}</v-list-item-subtitle>
                <v-list-item-subtitle class="pt-3">Address: {{issue.address}}</v-list-item-subtitle>
                <v-list-item-subtitle class="pt-3">Phone: {{issue.phoneNo}}</v-list-item-subtitle>
              </v-col>
              <v-col cols="12" sm="4">
                <v-btn class="ma-2" text icon color="blue lighten-2">
                  <v-icon>mdi-thumb-up</v-icon>
                  <span class="pb-10">{{issue.plusOnes.totalPlusOnes}}</span>
                </v-btn>

                <v-btn class="ma-2" text icon color="red lighten-2">
                  <v-icon>mdi-thumb-down</v-icon>
                  <span class="pb-10">{{issue.reports.totalReports}}</span>
                </v-btn>
                <v-btn class="ma-2" text icon color="green lighten-2">
                  <v-icon>mdi-checkbox-marked-circle</v-icon>
                  <span class="pb-10">{{issue.acknowledgedVolunteers.totalAcknowledgements}}</span>
                </v-btn>
                <v-btn class="mt-2" v-if="buttonText">{{buttonText}}</v-btn>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <v-tabs fixed-tabs background-color="yellow" dark v-model="tabs" show-arrows>
                  <v-tabs-slider color="teal lighten-3"></v-tabs-slider>
                  <v-tab color="black" href="#comments">
                    <span style="color:black">Comments</span>
                  </v-tab>
                  <v-tab color="black" href="#essentials">
                    <span style="color:black">Essentials</span>
                  </v-tab>
                  <v-tab color="black" href="#volunteers">
                    <span style="color:black">Volunteers</span>
                  </v-tab>
                  <v-tab color="black" href="#donors">
                    <span style="color:black">Donors</span>
                  </v-tab>
                </v-tabs>
                <v-tabs-items v-model="tabs">
                  <v-tab-item key="comments" value="comments">
                    <comments></comments>
                  </v-tab-item>
                  <v-tab-item key="essentials" value="essentials">
                    <essentials :essentials="issue.essentials"></essentials>
                  </v-tab-item>
                  <v-tab-item key="volunteers" value="volunteers">
                    <volunteer-list
                      :volunteers="issue.acknowledgedVolunteers.verifiedAcknowledgedVolunteers"
                    ></volunteer-list>
                  </v-tab-item>
                  <v-tab-item key="donors" value="donors">
                    <donor-list :donors="donors"></donor-list>
                  </v-tab-item>
                </v-tabs-items>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <div class="flex-grow-1"></div>
          <v-btn color="blue darken-1" text @click="closeIssueDetailPopup()">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>


<script>
import Comments from "@/components/Shared/Comments";
import Essentials from "@/components/Shared/Essentials";
import VolunteerList from "@/components/Shared/VolunteerList";
import DonorList from "@/components/Shared/DonorList";
import { getParticularIssue } from "@/api/victim";
import { getDonorsByIssueId } from "@/api/donor";

export default {
  name: "IssueDetail",
  props: ["issueId", 'closeIssueDetailPopup'],
  components: {
    Comments,
    Essentials,
    VolunteerList,
    DonorList
  },
  data() {
    return {
      donors: [],
      issue: {},
      dialog: true,
      tabs: null
    };
  },
  mounted() {
    getParticularIssue(this.issueId).then(res => {
      if (res.data) {
        this.issue = res.data;
      }
    });
    getDonorsByIssueId(this.issueId).then(res => {
      if (res.data) {
        this.donors = res.data.donors;
      }
    });
  }
};
</script>