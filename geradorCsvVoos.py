import csv
import random

cidades = ["RIO", "SAO", "CNF", "REC", "FOR", "SSA", "BEL"]
datas = ["15/06/2024", "16/06/2024", "17/06/2024", "18/06/2024"]
horarios = ["14:00", "15:00", "16:00"]
assentos = [55, 50, 45, 40, 35, 30]
precos = [300, 400, 500, 600, 700, 800]

with open('voos.csv', 'w', newline='') as csvfile:
    voo_writer = csv.writer(csvfile, delimiter=';')
    for origem in cidades:
        for destino in cidades:
            if origem != destino and random.random() < 0.6:  
                data = random.choice(datas)
                horario = random.choice(horarios)
                assentos_disponiveis = random.choice(assentos)
                preco = random.choice(precos)
                voo_writer.writerow([origem, destino, data, horario, f"{assentos_disponiveis} assentos", f"R$ {preco}"])
