local items = {}
for i = 1, ARGV[1] do
    local item = redis.call('RPOP', KEYS[1])
    if item then
        table.insert(items, item)
    else
        break
    end
end
return items