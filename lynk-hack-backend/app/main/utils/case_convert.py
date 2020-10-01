import re


def convert_snake_case_to_camel_case(obj_to_convert, deep=False):
    if type(obj_to_convert) != dict:
        return obj_to_convert
    converted_dict_obj = {}
    for snake_case_k in obj_to_convert:
        camel_case_k = re.sub('_([a-z])', lambda match: match.group(1).upper(), snake_case_k)
        value = obj_to_convert[snake_case_k]

        if type(value) == dict and deep:
            converted_dict_obj[camel_case_k] = convert_snake_case_to_camel_case(value, deep)
        elif type(value) == list and deep:
            converted_list_items = []
            for item in value:
                converted_list_items.append(convert_snake_case_to_camel_case(item, deep))
            converted_dict_obj[camel_case_k] = converted_list_items
        else:
            converted_dict_obj[camel_case_k] = obj_to_convert[snake_case_k]
    return converted_dict_obj
