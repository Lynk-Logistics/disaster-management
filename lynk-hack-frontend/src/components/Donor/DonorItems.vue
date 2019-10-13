<template>
  <div style="border: grey 1px solid;padding: 10px" id="donor-items">
    <v-row>
      <v-card color="#383636" class="mx-3 px-5 pb-3" dark>
        <v-card-title
          class="headline mx-0 my-0 px-0"
          style="text-align:center;color: white"
          color="black"
        >Start Donating..</v-card-title>
        <v-card-subtitle
          class="mb-3"
        >We make a living by what we get, but we make a life by what we give....</v-card-subtitle>
      </v-card>
    </v-row>
    <v-row v-for="i in rows" :key="i" class="my-3">
      <v-col cols="6" sm="6" xs="6">
        <v-autocomplete
          v-model="itemValues[i]"
          :items="items"
          label="Item Name"
          item-text="name"
          item-value="id"
          :search-input.sync="searchInput[i]"
        >
          <template slot="no-data">
            <v-col cols="12" sm="6">
              <v-btn @click="addItem(searchInput[i], i)">Add '{{searchInput[i]}}'</v-btn>
            </v-col>
          </template>
        </v-autocomplete>
      </v-col>
      <v-col cols="6" sm="6" xs="6">
        <v-autocomplete v-model="quantities[i]" :items="range" label="Quantity"></v-autocomplete>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="6" sm="6" xs="6">
        <v-btn
          @click="addNewRow()"
          color="green"
          width="100%"
        >{{isNoRow ? 'Donate' : 'Add New Row'}}</v-btn>
      </v-col>
      <v-col cols="6" sm="6" xs="6">
        <v-btn @click="submitDonation()" color="green" v-if="!isNoRow" width="100%">Submit</v-btn>
      </v-col>
    </v-row>
  </div>
</template>
    
<script>
import { getAllEssentials } from "@/api/essentials";
import { createDonation } from "@/api/donor";
import cookiemixin from "@/components/Shared/cookiemixin";

export default {
  name: "DonorItems",
  data() {
    return {
      donationName: "",
      items: [],
      range: ["1", "2", "3", "5", "10", "25", "50", "100", "500", "1000"],
      itemValues: {},
      quantities: {},
      searchInput: {},
      rows: 2
    };
  },
  mixins: [cookiemixin],
  methods: {
    addNewRow() {
      this.rows = this.rows + 1;
    },
    submitDonation() {
      var items = [];
      Object.values(this.itemValues).forEach((item, i) => {
        items.push({
          itemId: item,
          quantity: parseInt(this.quantities[i + 1])
        });
      });
      var data = {
        items
      };
      createDonation(this.getCookie("lynkUserId"), data).then(res => {
        location.reload();
        console.log(res.data);
      });
    },
    addItem(value, i) {
      console.log(value);
      createEssential(value).then(res => {
        if (res.data) {
          var id = res.data.insertedId;
          this.items.push({ id: id, name: value });
          this.essentialValues.push(id);
        }
      });
    }
  },
  computed: {
    isNoRow() {
      return this.rows === 0;
    }
  },
  mounted() {
    getAllEssentials().then(res => {
      if (res.data) {
        this.items = res.data.essentials;
      }
    });
  }
};
</script>