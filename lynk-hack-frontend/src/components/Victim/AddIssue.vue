<template>
  <v-dialog v-model="dialog" persistent max-width="600px">
    <v-card>
      <v-card-title>
        <span class="headline">User Profile</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" sm="12" md="12">
              <v-text-field v-model="name" label="Name*" required></v-text-field>
            </v-col>
            <v-col cols="12" sm="12" md="6">
              <v-text-field
                v-model="noOfPeople"
                label="No of people"
                hint="example of helper text only on focus"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="12" md="6">
              <v-text-field v-model="phoneNo" label="Phone Number" type="phone"></v-text-field>
            </v-col>
            <v-col cols="12" md="12">
              <v-textarea v-model="address" outlined name="input-7-4" label="Address"></v-textarea>
            </v-col>
            <v-col cols="12">
              <v-autocomplete
                v-model="essentialValues"
                :disabled="isUpdating"
                :items="essentials"
                filled
                chips
                color="blue-grey lighten-2"
                label="Select"
                item-text="name"
                item-value="id"
                :search-input.sync="searchInput"
                multiple
              >
                <template v-slot:selection="data">
                  <v-chip
                    v-bind="data.attrs"
                    :input-value="data.selected"
                    close
                    @click="data.select"
                    @click:close="remove(data.item)"
                  >{{ data.item.name }}</v-chip>
                </template>
                <template v-slot:item="data">
                  <template v-if="typeof data.item !== 'object'">
                    <v-list-item-content v-text="data.item"></v-list-item-content>
                  </template>
                  <template v-else>
                    <v-list-item-content>
                      <v-list-item-title v-html="data.item.name"></v-list-item-title>
                    </v-list-item-content>
                  </template>
                </template>
                <template slot="no-data">
                  <v-col cols="12" sm="6">
                    <v-btn @click="addItem(searchInput)">Add '{{searchInput}}'</v-btn>
                  </v-col>
                </template>
              </v-autocomplete>
            </v-col>
          </v-row>
        </v-container>
        <small>*indicates required field</small>
      </v-card-text>
      <v-card-actions>
        <div class="flex-grow-1"></div>
        <v-btn color="blue darken-1" text @click="closeAddIssuePopup()">Close</v-btn>
        <v-btn color="blue darken-1" text @click="addIssue()">Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { getAllEssentials, createEssential } from "@/api/essentials";
import { createIssue } from "@/api/victim";

export default {
  name: "AddIssue",
  props: ["closeAddIssuePopup"],
  data() {
    const srcs = {
      1: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
      2: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
      3: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
      4: "https://cdn.vuetifyjs.com/images/lists/4.jpg",
      5: "https://cdn.vuetifyjs.com/images/lists/5.jpg"
    };
    return {
      phoneNo: "",
      noOfPeople: "",
      address: "",
      essentials: [],
      dialog: true,
      searchInput: "",
      autoUpdate: true,
      essentialValues: [],
      isUpdating: false,
      name: ""
    };
  },
  watch: {
    isUpdating(val) {
      if (val) {
        setTimeout(() => (this.isUpdating = false), 3000);
      }
    },
    essentialValues(val) {
      console.log(val);
    }
  },

  methods: {
    remove(item) {
      const index = this.friends.indexOf(item.name);
      if (index >= 0) this.friends.splice(index, 1);
    },
    addItem(searchInput) {
      createEssential(searchInput).then(res => {
        if (res.data) {
          var id = res.data.insertedId;
          this.essentials.push({ id: id, name: searchInput });
          this.essentialValues.push(id);
          this.searchInput = "";
        }
      });
    },
    addIssue() {
      var data = {
        essentials: this.essentialValues,
        name: this.name,
        address: this.address,
        phoneNo: this.phoneNo,
        noOfPeople: this.noOfPeople
      };
      if (navigator && navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(position => {
          data["lat"] = position.coords.latitude;
          data["long"] = position.coords.longitude;
          createIssue(data).then(res => {
            console.log(res.data);
            location.reload();
            this.closeAddIssuePopup();
          });
        });
      }
    }
  },
  mounted() {
    getAllEssentials().then(res => {
      if (res.data) {
        this.essentials = res.data.essentials;
        console.log(this.essentials);
      }
    });
  }
};
</script>