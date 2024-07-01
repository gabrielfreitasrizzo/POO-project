import csv
import random

cidades = ["RIO", "SAO", "CNF", "REC", "FOR", "SSA", "BEL"]
hoteis = ["Hotel 1", "Hotel 2", "Hotel 3", "Hotel 4", "Hotel 5"]
vagas = [50, 60, 70, 80, 90, 100]
precos = [100, 200, 300, 400, 500, 600, 700, 800]
estrelas = [1, 2, 3, 4, 5]

with open('hoteis.csv', 'w', newline='') as csvfile:
    hotel_writer = csv.writer(csvfile, delimiter=';')
    for cidade in cidades:
        for hotel in hoteis:
            vagas_disponiveis = random.choice(vagas)
            preco_diaria = random.choice(precos)
            estrela = random.choice(estrelas)
            hotel_writer.writerow([cidade, hotel, vagas_disponiveis, f"R$ {preco_diaria}", estrela])
