from app.main.utils.case_convert import convert_snake_case_to_camel_case
import jsons


class BaseDTO:
    def to_json(self):
        return convert_snake_case_to_camel_case(jsons.dump(self), True)
