import csv
import random

nomes = [f"cliente {i}" for i in range(10000)]
cidades = ["RIO", "SAO", "CNF", "REC", "FOR", "SSA", "BEL"]
duracoes = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
estrelas = [1, 2, 3, 4, 5]
saldos = [2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000]

with open('clientes_10000.csv', 'w', newline='') as csvfile:
    cliente_writer = csv.writer(csvfile, delimiter=';')
    for nome in nomes:
        origem = random.choice(cidades)
        destino = random.choice(cidades)
        while destino == origem:
            destino = random.choice(cidades)
        duracao = f"{random.choice(duracoes)} dias"
        estrela = f"{random.choice(estrelas)} estrelas"
        saldo = f"R$ {random.choice(saldos)}"
        cliente_writer.writerow([nome, origem, destino, duracao, estrela, saldo])
