import csv
import pandas as pd
import matplotlib.pyplot as plt
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.automap import automap_base
from sqlalchemy import text
from declarative import Base, ReadingAverage, Reading

csvnames = ["agg/1.csv", "agg/2.csv"]
streamcsv = ["stream/data.csv"]

# engine = create_engine('sqlite:///sqlalchemy_sqllite.db')
# engine = create_engine('mysql+mysqlconnector://mysql:mysql@localhost:8182')
# engine = create_engine('mysql+pymysql://mysql:mysql@localhost:12346/datacrawlingkraken')
engine = create_engine('mysql+pymysql://mysql:mysql@192.168.0.102:12346/datacrawlingkraken')
print("Engine created....")

Base.metadata.create_all(engine)

print("Create all tables done....")
DBSession = sessionmaker(bind=engine)
session = DBSession()


def readingavgfromcsv():
    print("READING AVG")
    for path in csvnames:
        querytext = ""
        testdict = {}
        # print("'")
        x = 0
        with open(path, 'r') as f:
            data = csv.DictReader(f)
            #for name in data.fieldnames:
                #print("---")
                #print(name + "=float(testdict['" + name.lower() + "']),")

            for row in data:
                # print(row)
                for k, v in row.items():
                    # print(k + " " + v)
                    if not v:
                        v = 0
                    testdict[k] = v
                # print(testdict)
                new_row = ReadingAverage(Vehicle_Number=(testdict['Vehicle_Number']),
                                         Ambiant_Pressure=float(testdict['Ambiant_Pressure']),
                                         Canister_Controler=float(testdict['Canister_Controler']),
                                         Current_for_Injector_Opening=float(testdict['Current_for_Injector_Opening']),
                                         Distance_Km=float(testdict['Distance_Km']),
                                         Lambda_Adapation=float(testdict['Lambda_Adapation']),
                                         Gear_Engaged=float(testdict['Gear_Engaged']),
                                         Lambda_Control_1=float(testdict['Lambda_Control_1']),
                                         Lambda_Control_2=float(testdict['Lambda_Control_2']),
                                         MAF_Before_Compressor=float(testdict['MAF_Before_Compressor']),
                                         Engine_MAF=float(testdict['Engine_MAF']),
                                         Fuel_Mass_Adapation=float(testdict['Fuel_Mass_Adapation']),
                                         Engine_Speed=float(testdict['Engine_Speed']),
                                         Turbo_Turbine_Speed=float(testdict['Turbo_Turbine_Speed']),
                                         Injection_Number=float(testdict['Injection_Number']),
                                         Fuel_Pressure_at_Inlet_Pump=float(testdict['Fuel_Pressure_at_Inlet_Pump']),
                                         Fuel_Pressure=float(testdict['Fuel_Pressure']),
                                         Turbo_Waste_Gate_Position=float(testdict['Turbo_Waste_Gate_Position']),
                                         Pedal_Position=float(testdict['Pedal_Position']),
                                         Turbo_Waste_Gate_Controler=float(testdict['Turbo_Waste_Gate_Controler']),
                                         Water_Pump_Controler=float(testdict['Water_Pump_Controler']),
                                         Engine_Thermostat_Controler=float(testdict['Engine_Thermostat_Controler']),
                                         Engine_Air_Throttle_Controler=float(testdict['Engine_Air_Throttle_Controler']),
                                         Fuel_Pump_Delivery=float(testdict['Fuel_Pump_Delivery']),
                                         Segment_Number=float(testdict['Segment_Number']),
                                         Injector_Adapation1=float(testdict['Injector_Adapation1']),
                                         Injector_Adapation2=float(testdict['Injector_Adapation2']),
                                         Injector_Adapation3=float(testdict['Injector_Adapation3']),
                                         Injector_Adapation4=float(testdict['Injector_Adapation4']),
                                         Injector_Adapation5=float(testdict['Injector_Adapation5']),
                                         Injector_Adapation6=float(testdict['Injector_Adapation6']),
                                         Injector_Adapation7=float(testdict['Injector_Adapation7']),
                                         Injector_Adapation8=float(testdict['Injector_Adapation8']),
                                         Injector_Adapation9=float(testdict['Injector_Adapation9']),
                                         Injector_Adapation10=float(testdict['Injector_Adapation10']),
                                         Injector_Adapation11=float(testdict['Injector_Adapation11']),
                                         Injector_Adapation12=float(testdict['Injector_Adapation12']),
                                         Injector_Adapation13=float(testdict['Injector_Adapation13']),
                                         Injector_Adapation14=float(testdict['Injector_Adapation14']),
                                         Injector_Adapation15=float(testdict['Injector_Adapation15']),
                                         Injector_Adapation16=float(testdict['Injector_Adapation16']),
                                         Injector_Adapation17=float(testdict['Injector_Adapation17']),
                                         Injector_Adapation18=float(testdict['Injector_Adapation18']),
                                         Injector_Adapation19=float(testdict['Injector_Adapation19']),
                                         Injector_Adapation20=float(testdict['Injector_Adapation20']),
                                         Current_Time_for_Injector=float(testdict['Current_Time_for_Injector']),
                                         Pump_Adaptation_2=float(testdict['Pump_Adaptation_2']),
                                         Ambiant_Temperature=float(testdict['Ambiant_Temperature']),
                                         Engine_Coolant_Tempertaure=float(testdict['Engine_Coolant_Tempertaure']),
                                         ECU_Temperature=float(testdict['ECU_Temperature']),
                                         TUrbo_Temperature_before_Turbine=float(
                                             testdict['TUrbo_Temperature_before_Turbine']),
                                         Fuel_Temperature_Before_Pump=float(testdict['Fuel_Temperature_Before_Pump']),
                                         Fuel_Temperature_at_Pump_Outlet=float(
                                             testdict['Fuel_Temperature_at_Pump_Outlet']),
                                         Fuel_Temperature_In_Rail=float(testdict['Fuel_Temperature_In_Rail']),
                                         Engine_Inlet_Temperture=float(testdict['Engine_Inlet_Temperture']),
                                         time_30_s=float(testdict['time_30_s']),
                                         timestamp=int(testdict['timestamp']),
                                         Engine_Oil_Temperature=float(testdict['Engine_Oil_Temperature']),
                                         Trt=float(testdict['Trt']),
                                         Battery_Voltage=float(testdict['Battery_Voltage']),
                                         Voltage_Boost_Injector_control=float(
                                             testdict['Voltage_Boost_Injector_control']),
                                         Pump_Adaptation_1=float(testdict['Pump_Adaptation_1']),
                                         Waste_Gate_Adapatation_1=float(testdict['Waste_Gate_Adapatation_1']),
                                         Waste_Gate_Adapatation_2=float(testdict['Waste_Gate_Adapatation_2']),
                                         Vehicle_Speed=float(testdict['Vehicle_Speed']))
                # print("---")
                session.add(new_row)
                x = x + 1
                if (x % 50 == 0):
                    session.commit()
            session.commit()
            print("CSV ADDED")


readingavgfromcsv()