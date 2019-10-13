<template>
  <v-list three-line>
    <template v-for="(issue, index) in issues">
      <v-subheader v-if="issue.header" :key="issue.header" v-text="issue.header"></v-subheader>

      <v-divider v-else-if="issue.divider" :key="index" :inset="issue.inset"></v-divider>

      <v-list-item v-else :key="issue.title" @click="viewDetailedIssue()">
        <v-list-item-content>
          <v-row>
            <v-col cols="12" sm="8" @click="openIssueDetailDialog(issue['id'])">
              <v-list-item-title color="black">Title: {{issue.name}}</v-list-item-title>
              <v-list-item-subtitle class="pt-1">No. of people: {{issue.noOfPeople}}</v-list-item-subtitle>
              <v-list-item-subtitle class="pt-3">Address: {{issue.address}}</v-list-item-subtitle>
              <v-list-item-subtitle class="pt-3">Phone: {{issue.phoneNo}}</v-list-item-subtitle>
            </v-col>
            <v-col cols="12" sm="4">
              <v-tooltip left>
                <template v-slot:activator="{ on }">
                  <v-btn
                    class="ma-2"
                    text
                    icon
                    v-on="on"
                    color="blue lighten-2"
                    @click="plusOne(issue.id)"
                  >
                    <v-icon>mdi-thumb-up</v-icon>
                    <span class="pb-10">{{issue.plusOnes.totalPlusOnes}}</span>
                  </v-btn>
                </template>
                <span>
                  Anonymous +1 - "{{issue.plusOnes.anonymousPlusOnes}}"
                  <br />
                  Verified +1 - "{{issue.plusOnes.verifiedPlusOnes}}"
                </span>
              </v-tooltip>
              <v-tooltip left>
                <template v-slot:activator="{ on }">
                  <v-btn
                    class="ma-2"
                    text
                    icon
                    v-on="on"
                    color="red lighten-2"
                    @click="report(issue.id)"
                  >
                    <v-icon>mdi-thumb-down</v-icon>
                    <span class="pb-10">{{issue.reports.totalReports}}</span>
                  </v-btn>
                </template>
                <span>
                  Anonymous reports - "{{issue.reports.anonymousReports}}"
                  <br />
                  Verified reports - "{{issue.reports.verifiedReports}}"
                </span>
              </v-tooltip>
              <v-btn class="ma-2" text icon color="green lighten-2">
                <v-icon>mdi-checkbox-marked-circle</v-icon>
                <span class="pb-10">{{issue.acknowledgedVolunteers.totalAcknowledgements}}</span>
              </v-btn>
              <v-btn
                class="mt-2"
                :disabled="issue.isAcknowledgeButtonDisable"
                v-if="buttonText"
                @click="performAction(buttonText, issue.id)"
              >{{buttonText}}</v-btn>
            </v-col>
          </v-row>
        </v-list-item-content>
      </v-list-item>
    </template>
    <issue-detail
      v-if="showIssueDetailDialog"
      :issueId="issueId"
      :closeIssueDetailPopup="closeIssueDetailPopup"
    ></issue-detail>
    <authentication-dialog v-if="showAuthenticationPopup" userType="volunteer"></authentication-dialog>
  </v-list>
</template>

<script>
import IssueDetail from "@/components/Shared/IssueDetail.vue";
import { plusOne as plusOneAPI, report as reportAPI } from "@/api/victim";
import { acknowledgeIssue } from "@/api/volunteer";
import AuthenticationDialog from "@/components/Shared/AuthenticationDialog.vue";

export default {
  name: "ViewIssues",
  props: ["buttonText", "issues"],
  components: {
    IssueDetail,
    AuthenticationDialog
  },
  data() {
    return {
      isAcknowledgeButtonDisable: false,
      issueId: null,
      showIssueDetailDialog: false,
      showAuthenticationPopup: false
    };
  },
  methods: {
    viewDetailedIssue() {
      console.log("nice");
    },
    openIssueDetailDialog(issueId) {
      this.issueId = issueId;
      this.showIssueDetailDialog = true;
    },
    plusOne(issueId) {
      plusOneAPI(issueId).then(res => {
        console.log(res.data);
        this.issues.forEach(issue => {
          if (issue.id === issueId) {
            issue.plusOnes.totalPlusOnes = issue.plusOnes.totalPlusOnes + 1;
            issue.plusOnes.anonymousPlusOnes += 1;
          }
        });
      });
    },
    report(issueId) {
      reportAPI(issueId).then(res => {
        console.log(res.data);
        this.issues.forEach(issue => {
          if (issue.id === issueId) {
            issue.reports.totalReports = issue.reports.totalReports + 1;
            issue.reports.anonymousReports += 1;
          }
        });
      });
    },
    getCookie(name) {
      var ca = document.cookie.split(";");
      for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === " ") {
          c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
          return c.substring(name.length + 1);
        }
      }
    },
    performAction(action, issueId) {
      this.issueId = issueId;
      if (action === "Acknowledge") {
        if (this.getCookie("lynkUser")) {
          acknowledgeIssue(issueId, {
            phoneNo: this.getCookie("lynkUser")
          }).then(res => {
            console.log(res.data);
            this.issues.forEach(issue => {
              if (issue.id === issueId) {
                issue.acknowledgedVolunteers.totalAcknowledgements =
                  issue.acknowledgedVolunteers.totalAcknowledgements + 1;
                issue["isAcknowledgeButtonDisable"] = true;
                location.reload();
              }
            });
          });
        } else {
          this.showAuthenticationPopup = true;
        }
      }
    },
    closeIssueDetailPopup() {
      this.showIssueDetailDialog = false;
    }
  }
};
</script>

