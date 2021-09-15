function writeFoot(Lvalue,year,day,Footpoints,N)
addpath(genpath('d:\documents\matlab\LDiscrete'));
sday=num2str(day);
syear=num2str(year);

sL=num2str(Lvalue,'%.1f');
sL=strrep(sL,'.','_');
data=Footpoints;

filename=['LinesFoot_',sL,'_',syear,'_',sday,'_N',num2str(N),'.txt'];
myfile=fopen(filename,'wt');
n=size(data,1);
for i=1:n
    fprintf(myfile,'%.4f %.4f %.4f\n',data(i,:));
end
fclose(myfile);
end