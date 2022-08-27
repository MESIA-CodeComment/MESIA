import scipy.stats

#Investigate the correlation of manual scores and MESIA
if __name__=="__main__":
    MESIA = []
    Human1 = []
    Human2 = []
    Human3 = []
    with open("D:\CodeSumEvaluation\Human1") as f:
        data = f.readlines()
        Human1 = [float(s.strip()) for s in data]

    with open("D:\CodeSumEvaluation\Human2") as f:
        data = f.readlines()
        Human2 = [float(s.strip()) for s in data]
    with open("D:\CodeSumEvaluation\Human3") as f:
        data = f.readlines()
        Human3 = [float(s.strip()) for s in data]

    with open("D:\CodeSumEvaluation\MESIA") as f:
        data = f.readlines()
        MESIA = [float(s.strip()) for s in data]

    print(scipy.stats.spearmanr(MESIA, Human1))
    print(scipy.stats.spearmanr(MESIA, Human2))
    print(scipy.stats.spearmanr(MESIA, Human3))
    print(scipy.stats.spearmanr(Human1, Human2))
    print(scipy.stats.spearmanr(Human2, Human3))
    print(scipy.stats.spearmanr(Human1, Human3))