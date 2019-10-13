from victims.models import Needs

need1 = Needs(type="Goods", sub_type="Food")
need1.save()

need2 = Needs(type="Goods", sub_type="Clothes")
need2.save()

need3 = Needs(type="Goods", sub_type="Other Accessories")
need3.save()

need4 = Needs(type="Goods", sub_type="Medicines")
need4.save()

need5 = Needs(type="Emergency", sub_type=null)
need5.save()

need6 = Needs(type="Relocation", sub_type=null)
need6.save()