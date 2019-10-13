import React, { useState, useRef } from 'react';
import Select from 'react-select';

import './styles.scss';
import axios from 'axios';
import {Redirect} from 'react-router-dom';

function PostRequirement() {

    const [type, setType] = useState({value: null,label: null});

    const [goodsType, setGoodsType] = useState(null);

    const [needTransport, setNeedTransport] = useState(false);

    const inputRef = useRef(null);

    const quanRef = useRef(null);

    const [redirect,setRedirect] = useState(null);


    const typeOptions = [
        { value: 'goods', label: 'Goods' },
        { value: 'emergency', label: 'Medical Emergency' },
        { value: 'relocation', label: 'relocation' }
    ];

    const goodsOptions = [
        { value: 'food', label: 'Food' },
        { value: 'clothes', label: 'Clothes' },
        { value: 'money', label: 'Money' },
        { value: 'medicine', label: 'Medicine' },
        { value: 'others', label: 'Others' }
    ];

    const customStyles = {
        control: (provided, state) => ({
            ...provided,
            borderRadius: '25px',
            border: '1px solid #DFDFDF',
            borderColor: '#DFDFDF',
            boxShadow: 'none',
            backgroundColor: '#f3f3f3',
            width: '95%',
            "&:hover": {
                boxShadow: 'none'
            },
            fontSize: '0.9em'
        }),
        placeholder: () => ({
            display: 'none'
        }),
        dropdownIndicator: () => ({
            color: '#707070',
            padding: '0% 5%'
        }),
        menu: () => ({
            backgroundColor: '#f3f3f3',
            borderRadius: '25px',
            width: '95%'
        }),
        menuList: () => ({
            borderRadius: '25px'
        }),
        option: (provided, state) => ({
            ...provided,
            fontSize: '0.9em',
            fontFamily: "'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif",
            backgroundColor: state.isSelected ? '#707070' : (state.isFocused ? '#DFDFDF' : '#f3f3f3'),
            color: state.isSelected ? 'white' : 'hsl(0, 0%, 20%)',
            borderRadius: '25px',
            "&:active": {
                backgroundColor: state.isSelected ? '#707070' : '#DFDFDF'
            }
        }),
        indicatorSeparator: () => ({
            display: 'none'
        }),
        container: () => ({
            padding: '2% 0%'
        }),
        indicatorsContainer: () => ({
            padding: '0% 4%'
        })
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        let value = null;
        if( type.value === 'goods') {
            value = goodsType.value;
            //add quantity
        } else if (type.value === 'emergency') {
            value = inputRef.current.value;
        }
        else if(type.value === 'relocation'){

            value = {need_transportation: needTransport.value }
        } else {}
        let reqType = type.value;
        //post request
        let values = {
            'request_type': reqType ,
            'value': value ,
            'location': localStorage.getItem('location')
        };
        let id = localStorage.getItem('userId');
        axios.post('/victim/'+id+'/request')
        .then((response)=> {
            console.log(response);
        }).catch((error)=> {
            setRedirect(
                <Redirect to={{
                    pathname: '/error'
                }} />
            );
        });
    }

    return (
        <div className="post-requirement">
            {redirect}
            <div className = 'card'>
            <div className = 'header'>Post Your Requirement</div>
            <form >
                <label className='label'>Requirement Type</label>
                {console.log('type', type)}
                <Select
                    className='select'
                    styles={customStyles}
                    value={type}
                    onChange={(selectedOption) => setType(selectedOption)}
                    options={typeOptions}
                />
                {    type ? 
                     type.value === 'goods' ?
                        <span>
                            <label className='label'>Goods Type</label>
                            <Select
                                className='select'
                                styles={customStyles}
                                value={goodsType}
                                onChange={(selectedOption) => setGoodsType(selectedOption)}
                                options={goodsOptions}
                            />
                            <label className = 'label'>Quantity</label>
                            <input className='input' type='text' ref={quanRef} />
                        </span> :
                        type.value === 'emergency' ?
                            <span>
                                <label className='label'>Details of Medication Needed</label>
                                <textarea ref={inputRef} />
                            </span>
                            :
                         type.value === 'relocation' ?
                            //display safe places
                            <span>
                                <label className='label'>Do you need transportation ?</label>
                                <Select
                    className='select'
                    styles={customStyles}
                    value={needTransport}
                    onChange={(selectedOption) => setNeedTransport(selectedOption)}
                    options={[{label: 'Yes', value: true}, {label: 'No',value: false}]}
                />
                            </span>
                            :
                            <div></div>
                            :
                            <div></div>
            
                }
               <button className='button' onClick={(event)=>handleSubmit(event)} disabled={false}>Submit</button>
            </form>
            </div>
        </div>
    );
}

export default PostRequirement;
