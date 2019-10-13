import React,{useState} from 'react';
import './styles.scss';

function TrackStatus(props) {

    //console.log('props',props);

    let obj = {
        status: 4,
        need: 'goods',
        sub_need: 'clothes',
        victim_details: {
          name: 'abc',
          mobile: '367636763673',
          location: 'dhhhd'
        },
        supplier_details: {
          name: 'sabc',
          mobile: '367636763673',
          location: 'dhhhd'
        },
        volunteer_details: {
          name: 'vabc',
          mobile: '367636763673',
          location: 'dhhhd'
        },
    }

    //change userType 
    let userType = localStorage.getItem('userType');

    let statusList = [];

    let currentStatus = obj.status;

    //const [currentStatus,setCurrentStatus] = useState(null);

    if(userType === 'victim') {
        
        if(obj.need === 'goods') {

            statusList = [
                'Searching for a volunteer to be assigned',
                'Volunteer '+ obj.volunteer_details.name + ' has been assigned to help you out',
                'Volunteer '+obj.volunteer_details.name + ' is picking up the goods',
                'Volunteer '+obj.volunteer_details.name+' has picked up the goods and is on the way to your location',
                'Volunteer '+obj.volunteer_details.name+' has arrived to your place',
                'Delivered'
            ];
        } else if(obj.need === 'relocation') {

            if(obj.status === 4) {
                currentStatus = 3;
            } else if(obj.status === 6) {
                currentStatus = 4;
            } 
            
            statusList = [
                'Searching for a volunteer to be assigned',
                'Volunteer '+ obj.volunteer_details.name + ' has been assigned to help you out',
                'Volunteer '+obj.volunteer_details.name+' is on the way to your location',
                'Relocated'
            ];

        } else {

            if(obj.status === 4) {
                currentStatus = 3;
            } else if(obj.status === 6) {
                currentStatus = 4;
            } 

            statusList = [
                'Searching for a volunteer to be assigned',
                'Volunteer '+ obj.volunteer_details.name + ' has been assigned to help you out',
                'Volunteer '+obj.volunteer_details.name+' is on the way to your location',
                'Medical Assistance Delivered'
            ];
        }
    }
    else if(userType === 'supplier') {

      //  setCurrentStatus(props.obj.status);

        statusList = [
                'Searching for a volunteer to be assigned',
                'Volunteer '+ obj.volunteer_details.name + ' has been assigned to pick up your goods',
                'Volunteer '+obj.volunteer_details.name + ' has reached your place',
                'Volunteer '+obj.volunteer_details.name+' has picked up your goods and is on the way to the victim location',
                'Volunteer '+obj.volunteer_details.name+' has arrived to the victim location',
                'Your goods has been delivered'
        ];
    } else if(userType === 'volunteer') {

        if(obj.need === 'goods') {

            if(obj.status === 5) {
                currentStatus = 4;
            }
         /*   else {
                surrentStatus(props.obj.status);
            }*/

            statusList = [
                'Searching for requirements',
                'You have been assigned to transfer goods from '+obj.supplier_details.name+' to '+obj.victim_details.name,
                'You have reached the place to pick up goods',
                'You have reached the victim place to deliver goods'
            ];

        } else {

            if(obj.status === 5) {
                currentStatus = 3;
            } /*else {
                setCurrentStatus(props.obj.status);
            }*/

            statusList = [
                'Searching for requirements',
                'You have been assigned to help '+obj.victim_details.name+' out',
                'You have reached the victim place'
            ];

        }
    }

    const isActive = (index) => {
        let name = 'status-card-';
        let active = index === (currentStatus-1) ? 'enabled': 'disabled';
        return name+active;
    }

    

  
                    

  return (
    <div className="track-status">
        {console.log('statuscurrent ',currentStatus)}
       <div className='header'>Track Your Status</div>
         {statusList.map((item,index)=> <div className={isActive(index)} ><button className='button-status'>{index+1}</button><span>{item}</span></div>)}
    </div>
  );
}

export default TrackStatus;
