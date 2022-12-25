;**
;**	Logics for room 265 -- Tawni's closeup
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 17, 1989
;**


(script#				265)

(include	"face.sh")

(public
	rm265				0
)

;(define	vTawniFace					265)

(synonyms
	(woman tawni )
)

(enum												;**	RoomScript state values
	intro
	Bingo
	goodbye
	outtaHere
)

(local
	work
)


(instance rm265 of Room
	(properties
		picture		265
	)

	(method (init)
		(if (ego has iCreditCard)
			(Load VIEW iCreditCard)
		)

		(super init)

		(self
			setRegions		regBigFace,
			setLocales		locGirls,
			setScript 		RoomScript
		)

		(Notify regBigFace EyeWest		115  77)
		(Notify regBigFace EyeEast		171  82)
		(Notify regBigFace EyelidWest	114  82)
		(Notify regBigFace EyelidEast	169  87)
		(Notify regBigFace Nose			139 121)
		(Notify regBigFace Mouth		140 136)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
	)


	(method (handleEvent event &tmp theObj)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'go, take//mall, shopping')
					(Said 'go, take/shopping, mall'))
				(PrintL "\"Say, babe, why don't I take you shopping down at
					the famous Nontoonyt Mall?\"")
				(PrintP "\"I don't need you to take me `malling.'\"")
			)

			((or	(Said '(go<out), date/patti')
					(Said 'make/love, date')
					(Said 'lets<make/love, date')
					(Said '/make/love')
					(Said 'ask, take, go/room, place, comic, club, date, walk, bar, show, home, dance')
					(Said 'ask, take, go//room, place, comic, club, date, walk, bar, show, home, dance'))
				(PrintL "\"What do you say, Tawni?
					Don't all you beach girls have a thing for older, pudgy
					formerly-bald guys?\"")
				(PrintP "\"Ha, ha. You really have a sense of humor!
					What would make me interested in you?\"")
			)

			((Said 'sell')
				(PrintP "\"I don't want to buy from an American like
					you when I could purchase native island souvenirs.\"")
				(React rSmile 20)
			)

			((Said '/souvenir')
				(PrintL "\"Tawni, do you recommend the souvenirs here on the 
					beach?\"")
				(PrintP "\"Boy, do I! 
					Larry, I bet I've bought something from every native
					vendor that's appeared here!\"")
			)

			((Said '/swimming, ocean, water')
				(PrintL "\"So, Tawni, how do you like swimming in the water 
					here?\"")
				(PrintP "\"It's wonderful, but my suit does get strange stains!\"")
			)

			((or	(Said 'stop/look')
					(Said 'look<stop')
					(Said 'rise, (stand<up), (get<off, up)')
					(Said 'look/beach, room')
					(Said 'quit, leave, exit, quit, walk, go'))
				(self changeState goodbye)
			)

			((Said 'fuck')
				(PrintP "\"%s Shame on you!\"" expletive)
				(React rFrown 8)
			)

			((Said 'show/prick')
				(PrintL "\"Hey, Tawni! Bet you don't have one of these!\"")
				(PrintP "\"Gee, Larry, you almost don't have one either!\"")
				(React rSmile 20)
			)

			((Said '/weather')
				(PrintP "\"Everybody talks about it but you don't do a thing 
					about it_-- oh, wait! Did you say weather?\"")
			)

			((Said 'make/joke')
				(Print "You attempt to make a lame joke.")
				(Print "(You succeed.)" LOW)
				(PrintP "\"Hey, Larry! This is what I think of that joke!\"")
				(React rSilly 22)
			)

			((Said 'love/you')
				(PrintL "\"Tawni, could this be love?\"")
				(PrintP "\"No, but it might be like!\"")
				(React rLove 44)
			)

			((or	(Said '(out<go), dance, date/')
					(Said 'take/girl/home')
					(Said 'ask/girl/date')
					(Said 'go/room, place, comic, club, date, walk, bar, show, dance'))
				(PrintL "\"Would you care to leave this beach and go somewhere
					where we could be alone?\"")
				(PrintP "\"Only to Nordstrom's!\"")
			)

			((Said '/mall')
				(PrintL "\"What's the story on that downtown Nontoonyt Mall,
					Tawni? I can't seem to find it_-- anywhere on the island!\"")
				(PrintP "\"If I knew, would I be here on this towel?\"")
			)

			((Said 'talk')
				(switch (Random 1 3)
					(1
						(PrintL "\"Have you been to that new downtown Nontoonyt
							Mall?\"")
						(PrintP "\"No, but if I ever find it,
							I bet I could spend my whole vacation there!\"")
					)
					(2
						(PrintL "\"Have I mentioned to you that I will soon be
							coming into a major real-estate holding?\"")
						(PrintP "\"Is it a shopping mall?\"")
					)
					(else
						(PrintL "\"What would it take for you to date an, um,
							`older man' like me?\"")
						(PrintP "\"Larry, I guess I'm just a `material girl!'\"")
					)
				)
			)

			((Said 'look>')
				(cond
					((Said '/leg')
						(Print "Those legs reach all the way to her beachhead!")
						(PrintP "\"Oh, Larry!\" smiles Tawni.
							\"Stop looking at me like that!\"")
						(React rSexy 22)
					)

					((Said '/skin')
						(Print "It's gorgeous.")
						(Print "(And you can see almost all of it!)" LOW)
					)

					((Said '/tit')
						(if (>= filthLevel DIRTY)
							(Print "They are sweet, aren't they?")
							(PrintP "\"Oh, Larry!\" 
								smiles Tawni, noticing your glance.
								\"You really enjoy staring at breasts, don't you?\"")
						else
							(Print "You think she seemed slow to cover them when
								she stood up!")
							(PrintP "\"Oh, Larry!\" 
								smiles Tawni, noticing your glance.
								\"Stop looking at me there!\"")
						)
						(React rOh 12)
					)

					((Said '/eye')
						(Print "Her blue eyes show the effects of
							too many hours spent \"malling.\"")
					)

					((Said '/nose')
						(Print "Tawni's pert little nose has years of
							experience sniffing out bargains.")
					)

					((Said '/necklace')
						(Print "24k gold")
						(Print "(You have extremely sharp vision!)" LOW)
					)

					((Said '/mouth')
						(Print "Her mouth is just beneath her nose.")
					)

					((Said '/tongue')
						(Print "Her tongue is scarred with paper cuts
							from licking too many credit card envelopes.")
					)

					((Said '/face')
						(Print "Tawni's face is framed with blond hair.")
					)

					((Said '/hair')
						(Print "Tawni's hair shows the effects of the many
							hours she devotes to sunbathing.")
							;**	"sunbathing" spell-checked May 27, 1989
					)

					((Said '/ass, bottom')
						(Print "It's barely covered by that string bikini.")
					)

					((Said '/pussy')
						(Print "There may indeed be faint outlines visible through
							that knit bikini...")
						(PrintP "\"Oh, Larry!\" smiles Tawni.
							\"Stop looking at me there!\"")
						(React rOh 12)
					)

					((Said '[/room, beach, woman]')
						(Print "Her magnificent blond hair haunts you.")
						(Print "(Too bad there's no one home within it!)" LOW)
						(React rSmile 33)
					)
				)
			)

			((Said 'show>')
				(= theObj (inventory saidMe))
				(event claimed FALSE)
				(cond
					((Said '[/noword]')
						(Print "What did you want to show her?")
					)
					((or	(not theObj)
							(not (theObj ownedBy ego)))
						(DontHave)
					)
					((== theObj (inventory at iWood))
						(PrintP "\"Geez, Larry, how nice!
							A stupid old hunk of wood?!\"")
						(React rFrown)
					)
					((== theObj (inventory at iSoap))
						(PrintP "\"Golly, gee, a REAL bar of soap!
							You're so lucky to have a real
							bar of soap.\"")
						(React rFrown)
					)
					((== theObj (inventory at iOrchids))
						(PrintP "\"Lovely bunch of wildflowers, Larry.
							You know those grow wild all over this island?\"")
						(React rFrown)
					)
					((== theObj (inventory at iCreditCard))
						(PrintP "\"OOOOHHHH! 
							Now there's something that `earns my interest!'\"")
					)
					(else
						(PrintP "\"Why, Larry,\" says Tawni,
							\"isn't that special?\"")
					)
				)
				(event claimed TRUE)
			)

			((Said 'give>')
				(= theObj (inventory saidMe))
				(event claimed FALSE)
				(cond
					((Said '[/noword]')
						(Print "What did you want to give her?")
					)
					((not theObj)
						(Print "It's not yours to give.")
					)
					((not	(theObj ownedBy ego))
						(DontHave)
					)
					((== theObj (inventory at iWood))
						(PrintP "\"Geez, Larry,
							I've always wanted a stupid old hunk of wood.
							Is this from the Neiman-Marcus catalog...\n\n
							...of 1812?!\"")
						(React rFrown)
					)
					((== theObj (inventory at iSoap))
						(PrintP "\"Golly, gee, a REAL bar of soap!
							In all my days of shopping,
							I've never seen a bar of soap before.\"")
						(React rFrown)
					)
					((== theObj (inventory at iOrchids))
						(PrintP "\"Lovely bunch of wildflowers, Larry.
							You're a regular little Merlin Olsen!\"")
						(React rFrown)
					)
					((== theObj (inventory at iCreditCard))
						(if (not (ego has iCreditCard))
							(DontHave)
						else
							(self changeState Bingo)
						)
					)
					(else
						(PrintP "\"Why, thank you, honey,\" says Tawni,
							\"but I need something I can spend!\"")
					)
				)
				(event claimed TRUE)
			)

			((Said 'get')
				(PrintP "\"Honey, I'm not interested in giving.
					It's GETTING I enjoy!\"")
				(React rSmile 22)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**	He gives her the credit card.

			(Bingo
				(HandsOff)
				(PrintL "\"Here, Tawni,\" you say,
					\"I can see how you enjoy shopping.
					I'd like you to have my credit card.\""
					#icon iCreditCard 0 0
				)
				(theGame changeScore 50)
				((Inventory at iCreditCard)
					owner		LIMBO,
;**						said		'/noword[<noword]',
				)
				(PrintP "\"Oh, Larry,\" cries Tawni,
					\"it's the perfect gift; the right size,
					the right shape, and the right color: gold!
					And I think I know the perfect way to
					express my appreciation to you, too. 
					Come here, big boy!\"")
				(= egoState DOING_IT)
				(++ state)						;**	skip next state
				(React rSmile 99)
				(= cycles 50)
			)

			(goodbye
				(Print "As much as you hate to,
					you stop looking at the beautiful Tawni.")
				(React rSmile 55)
				(= cycles 50)
			)

			(outtaHere
				(curRoom newRoom 260)
			)
		)
	)
)
;				(PrintL "\"\"")
;				(PrintP "\"\"")
